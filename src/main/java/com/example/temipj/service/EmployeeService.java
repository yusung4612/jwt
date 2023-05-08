package com.example.temipj.service;

import com.example.temipj.domain.UserDetailsImpl;
import com.example.temipj.domain.admin.Admin;
import com.example.temipj.domain.employee.Department;
import com.example.temipj.domain.employee.Division;
import com.example.temipj.domain.employee.Employee;
import com.example.temipj.dto.requestDto.EmployeeRequestDto;
import com.example.temipj.dto.responseDto.*;
import com.example.temipj.exception.CustomException;
import com.example.temipj.exception.ErrorCode;
import com.example.temipj.jwt.TokenProvider;
import com.example.temipj.repository.DepartmentRepository;
import com.example.temipj.repository.DivisionRepository;
import com.example.temipj.repository.EmployeeRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;

@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final TokenProvider tokenProvider;

    private final DepartmentRepository departmentRepository;

    private final DivisionRepository divisionRepository;

    // 직원 등록
    @Transactional
    public EmpResponseDto createEmp(Long departmentId, EmployeeRequestDto requestDto, HttpServletRequest request) {

        // 1. 토큰 유효성 확인
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " 부분 제외하고 토큰만 추출
        }
        if (!tokenProvider.validateToken(token)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

//        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
//        if (!tokenProvider.validateToken(request.getHeader("Authorization"))) {
//            throw new CustomException(ErrorCode.INVALID_TOKEN);
//        }

        // 2. tokenProvider Class의 SecurityContextHolder에 저장된 Admin 정보 확인
        Admin admin = (Admin) tokenProvider.getAdminFromAuthentication();
        if (null == admin) {
            throw new CustomException(ErrorCode.ADMIN_NOT_FOUND);
        }

        System.out.println("admin ************************");
        System.out.println(admin.getEmailId());

        // 3. 하위부서 유무 확인
        Department department = isPresentDepartment(departmentId);
//        Department department = departmentRepository.findById(departmentId);
        if (null == department) {
            throw new CustomException(ErrorCode.NOT_EXIST_DEPARTMENT);
        }
        // 4. 등록
        if (requestDto.getName().isEmpty())
            throw new CustomException(ErrorCode.NOT_BLANK_NAME);

        Employee employee = Employee.builder()
                .name(requestDto.getName())
                .birth(requestDto.getBirth())
                .extension_number(requestDto.getExtension_number())
                .mobile_number(requestDto.getMobile_number())
                .email(requestDto.getEmail())
                .leader("false")
                .department(department)
                .build();
        System.out.println("employee = " + employee.getLeader());

        employeeRepository.save(employee);

        Employee version = employeeRepository.findTop1ByOrderByModifiedAtDesc();
        String recentVersion = version.getModifiedAt().format((DateTimeFormatter.ofPattern("yyyyMMdd")));

        return EmpResponseDto.version(recentVersion, EmployeeResponseDto.builder()
                .name(employee.getName())
                .birth(employee.getBirth())
                .extension_number(employee.getExtension_number())
                .mobile_number(employee.getMobile_number())
                .build());
    }

    // 직원별 enabled 체크
    @Transactional
    public String enabledCheck(UserDetailsImpl userDetails) {
        if (userDetails != null) {
            return "1";
        } else {
            return "0";
        }
    }

    // 전체 직원 조회(Web)
    @Transactional
    public EmpResponseDto<?> getEmployeeAll(UserDetailsImpl userDetails, HttpServletRequest request) {
        // 토큰 유효성 확인
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " 부분 제외하고 토큰만 추출
        }
        if (!tokenProvider.validateToken(token)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        List<Employee> employeeList = employeeRepository.findAllByOrderByCreatedAtDesc();
        List<EmpListDto> employeeResponseDtoList = new ArrayList<>();

        for (Employee employee : employeeList) {
            Department department = employee.getDepartment();

            employeeResponseDtoList.add(EmpListDto.builder()
                    .name(employee.getName())
                    .birth(employee.getBirth())
                    .department(employee.getDepartment().getDepartment())
                    .extension_number(employee.getExtension_number())
                    .mobile_number(employee.getMobile_number())
                    .enabled(enabledCheck(userDetails))
                    .division(department.getDivision().getDivision())
                    .email(employee.getEmail())
                    .build());
        }
        Employee version = employeeRepository.findTop1ByOrderByModifiedAtDesc();
        String recentVersion = version.getModifiedAt().format((DateTimeFormatter.ofPattern("yyyyMMdd")));

        return EmpResponseDto.version(recentVersion, employeeResponseDtoList);
    }

    // 전체 직원 조회(Temi reponse)
    @Transactional
    public EmpResponseDto<?> getEmployeeAllList(UserDetailsImpl userDetails, HttpServletRequest request) {
        // 토큰 유효성 확인
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " 부분 제외하고 토큰만 추출
        }
        if (!tokenProvider.validateToken(token)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        List<Employee> employeeList = employeeRepository.findAllByOrderByCreatedAtDesc();
        List<EmployeeResponseDto> empListDtoList = new ArrayList<>();

        for (Employee employee : employeeList) {
            Department department = employee.getDepartment();

            empListDtoList.add(EmployeeResponseDto.builder()
                    .name(employee.getName())
                    .birth(employee.getBirth())
                    .extension_number(employee.getExtension_number())
                    .mobile_number(employee.getMobile_number())
                    .enabled(enabledCheck(userDetails))
                    .division(department.getDivision().getDivision())
                    .build());
        }
        Employee version = employeeRepository.findTop1ByOrderByModifiedAtDesc();
        String recentVersion = version.getModifiedAt().format((DateTimeFormatter.ofPattern("yyyyMMdd")));

        return EmpResponseDto.version(recentVersion, empListDtoList);
    }

    // 특정 직원 조회
    @Transactional
    public ResponseDto<?> getEmployee(Long id, HttpServletRequest request) {
        // 토큰 유효성 확인
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " 부분 제외하고 토큰만 추출
        }
        if (!tokenProvider.validateToken(token)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        // 직원 유무 확인
        Employee employee = isPresentEmployee(id);
        if (null == employee) {
            throw new CustomException(ErrorCode.NOT_EXIST_EMPLOYEE);
        }
//        Employee version = employeeRepository.findTop1ByOrderByModifiedAtDesc();
//        String recentVersion = version.getModifiedAt().format((DateTimeFormatter.ofPattern("yyyyMMdd")));

//        return ResponseDto.success(recentVersion,employee);
        return ResponseDto.success(employee);
    }

    //직원 정보 수정
    @Transactional
    public EmpResponseDto<?> updateEmp(Long id, EmployeeRequestDto requestDto, HttpServletRequest request) {
        // 1. 토큰 유효성 확인
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " 부분 제외하고 토큰만 추출
        }
        if (!tokenProvider.validateToken(token)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

//        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
//        if (!tokenProvider.validateToken(request.getHeader("Authorization"))) {
//            throw new CustomException(ErrorCode.INVALID_TOKEN);
//        }
        // 2. 직원 유무 확인
        Employee employee = isPresentEmployee(id);
        if (null == employee) {
            throw new CustomException(ErrorCode.NOT_EXIST_EMPLOYEE);
        }
        // 3. tokenProvider Class의 SecurityContextHolder에 저장된 Admin 정보 확인
        Admin admin = (Admin) tokenProvider.getAdminFromAuthentication();
        if (null == admin) {
            throw new CustomException(ErrorCode.ADMIN_NOT_FOUND);
        }
        // 4. 수정
        employee.update(requestDto);

        Employee version = employeeRepository.findTop1ByOrderByModifiedAtDesc();
        String recentVersion = version.getModifiedAt().format((DateTimeFormatter.ofPattern("yyyyMMdd")));

        return EmpResponseDto.version(recentVersion, employee);
    }

    //직원 삭제
    public EmpResponseDto<?> deleteEmp(Long id, HttpServletRequest request) {
        // 1. 토큰 유효성 확인
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " 부분 제외하고 토큰만 추출
        }
        if (!tokenProvider.validateToken(token)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

//        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
//        if (!tokenProvider.validateToken(request.getHeader("Authorization"))) {
//            throw new CustomException(ErrorCode.INVALID_TOKEN);
//        }
        // 2. 직원 유무 확인
        Employee employee = isPresentEmployee(id);
        if (null == employee) {
            throw new CustomException(ErrorCode.NOT_EXIST_EMPLOYEE);
        }
        // 3. SecurityContextHolder에 저장된 Admin 확인
        Admin admin = (Admin) tokenProvider.getAdminFromAuthentication();
        if (null == admin) {
            throw new CustomException(ErrorCode.ADMIN_NOT_FOUND);
        }
        // 4. 삭제
        employeeRepository.delete(employee);

        Employee version = employeeRepository.findTop1ByOrderByModifiedAtDesc();
        String recentVersion = version.getModifiedAt().format((DateTimeFormatter.ofPattern("yyyyMMdd")));

        return EmpResponseDto.version(recentVersion, "해당 직원이 삭제되었습니다.");
    }

    //직원 검색
    @Transactional
    public ResponseDto<?> searchEmployee(String keyword, UserDetailsImpl userDetails) {
        List<Employee> employeeList = employeeRepository.searchEmp(keyword);
        // 검색된 항목 담아줄 리스트 생성
        List<EmpListDto> employeeListResponseDtoList = new ArrayList<>();
        //for문을 통해서 List에 담아주기
        for (Employee employee : employeeList) {
            employeeListResponseDtoList.add(EmpListDto.builder()
//                            .id(employee.getId())
                    .name(employee.getName())
                    .birth(employee.getBirth())
                    .extension_number(employee.getExtension_number())
                    .mobile_number(employee.getMobile_number())
                    .division(employee.getDepartment().getDivision().getDivision())
                    .department(employee.getDepartment().getDepartment())
                    .email(employee.getEmail())
                    .enabled(enabledCheck(userDetails)).build());
        }
        return ResponseDto.success(employeeListResponseDtoList);
    }


    // 리더 선택
    @Transactional
    public ResponseDto<?> leaderSelect(Long id, HttpServletRequest request) {
        // 1.토큰 유효성 확인
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " 부분 제외하고 토큰만 추출
        }
        if (!tokenProvider.validateToken(token)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        // 2. 직원 확인
        Employee employee = isPresentEmployee(id);
        if (null == employee) {
            return ResponseDto.fail(ErrorCode.NOT_EXIST_EMPLOYEE.name(), ErrorCode.NOT_EXIST_EMPLOYEE.getMessage());
        }

        if (employee.getLeader().contains("false")) {
            employee.updateLeader(id);
            return ResponseDto.success("리더 지정 완료");

        } else {
            employee.cancelLeader(id);
        }
        employeeRepository.save(employee);

        return ResponseDto.success("리더 지정 해제");
    }

    // 선택한 리더 목록 조회
    @Transactional
    public Map getLeaderAll() {
        List<Division> division = divisionRepository.findAll();
        List<Employee> leaders = employeeRepository.getAllLeaders();
        Employee version = employeeRepository.findTop1ByOrderByModifiedAtDesc();
        String recentVersion = version.getModifiedAt().format((DateTimeFormatter.ofPattern("yyyyMMdd")));

        List<TestResponse> empList = new ArrayList<>();
        List<TestResponse> divisionList = new ArrayList<>();

        for (Employee e : leaders) {
            empList.add(
                    TestResponse.empOf(e)
            );
        }
        for (Division d : division) {
            divisionList.add(
                    TestResponse.divisionOf(d)
            );
        }

        // 원하는 방식으로 response 하기
        Map obj = new LinkedHashMap(); //root
        obj.put("version", recentVersion);
        LinkedList result = new LinkedList(); // division : [  배열선언

        for (int i = 0; i < divisionList.size(); i++) {
            String divisionName = divisionList.get(i).getDivision();

            LinkedList divisionObj = new LinkedList(); // 배열 선언 // R&D [ , 영업 [ ...

            for (int j = 0; j < empList.size(); j++) { // 직원이 10명이면 10번돈다
                String empDivision = empList.get(j).getDivision();

                if (divisionName.equals(empDivision)) { //for문 돌 때 원하는 직원만 꺼내오려고 if문으로 필터 처리

                    Map contactObj = new LinkedHashMap();
                    contactObj.put("name", empList.get(j).getName());
                    contactObj.put("mobile_number", empList.get(j).getMobile());
                    contactObj.put("email", empList.get(j).getEmail());

                    Map contactObj2 = new LinkedHashMap();
                    contactObj2.put("department", empList.get(j).getDepartment());
                    contactObj2.put("contact", contactObj);

                    // "R&D": [{ contactObj2 }]
                    divisionObj.add(contactObj2); //R&D [ {contacObj2}
                }
            }

            Map contactObj3 = new LinkedHashMap();
            contactObj3.put(divisionName, divisionObj);
            result.add(contactObj3); // [ { "R&D"~   =  [ contactObj3
        }
        obj.put("division", result); //"division": result

        return obj;
    }


    @Transactional
    public Employee isPresentEmployee(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee.orElse(null);
    }

    @Transactional
    public Department isPresentDepartment(Long id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment.orElse(null);
    }

    //    @Transactional(readOnly = true)
    @Transactional
    public Admin validateAdmin(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Authorization"))) {
//      => HttpServletRequest에서 "Refresh-Token"이라는 이름의 값을 Header에서 get해서
//      tokenProvider에 있는 validateToken method의 매개변수로 쏴준다.
            return null; //=> 유효성 검사 통과x
        }  //=> 통과되면 tokenProvider.getMemberFromAuthentication으로 go!
        return tokenProvider.getAdminFromAuthentication();
    }

}