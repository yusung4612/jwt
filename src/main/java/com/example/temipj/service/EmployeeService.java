package com.example.temipj.service;

import com.example.temipj.domain.UserDetailsImpl;
import com.example.temipj.domain.admin.Admin;
import com.example.temipj.domain.employee.Department;
import com.example.temipj.domain.employee.Division;
import com.example.temipj.domain.employee.Employee;
import com.example.temipj.dto.requestDto.EmployeeRequestDto;
import com.example.temipj.dto.responseDto.*;
import com.example.temipj.dto.responseDto.TestDto.MapperDto;
import com.example.temipj.dto.responseDto.TestDto.ResponseFirstDto;
import com.example.temipj.dto.responseDto.TestDto.ResponseSecondDto;
import com.example.temipj.dto.responseDto.TestDto.ResponseThirdDto;
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

//    Map<Long, Employee> map = new HashMap<>();

    private final EmployeeRepository employeeRepository;

    private final TokenProvider tokenProvider;

    private final DepartmentRepository departmentRepository;

    private final DivisionRepository divisionRepository;

    // 직원 등록
    @Transactional
    public EmpResponseDto createEmp(String departmentId, EmployeeRequestDto requestDto, HttpServletRequest request) {
        // 1. 토큰 유효성 확인
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        // 2. tokenProvider Class의 SecurityContextHolder에 저장된 Admin 정보 확인
        Admin admin = (Admin) tokenProvider.getAdminFromAuthentication();
        if (null == admin) {
            throw new CustomException(ErrorCode.ADMIN_NOT_FOUND);
        }
        // 3. 하위부서 유무 확인
//        Department department = isPresentDepartment(id);
        Department department = departmentRepository.findById(departmentId);
        if (null == department) {
            throw new CustomException(ErrorCode.NOT_EXIST_DEPARTMENT);
        }
        // 3. 등록
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
                // .id(employee.getId())
                .name(employee.getName())
                .birth(employee.getBirth())
                .extension_number(employee.getExtension_number())
                .mobile_number(employee.getMobile_number())
                .build());
    }


    //직원별 enabled 체크
    @Transactional
    public String enabledCheck(UserDetailsImpl userDetails) {
        if (userDetails == null) {
            return "1";
        } else {
//        boolean isCheckedLeader = leaderRepository.existsByAdminAndEmployee(userDetails.getAdmin(), employee);
//        boolean isCheckedLeader = employeeRepository.existsByEmployee(employee);

//        return isCheckedLeader ? "1" : "0"
            return "0";
        }
    }

    //전체 직원 조회
    @Transactional
    public EmpResponseDto<?> getEmployeeAll(UserDetailsImpl userDetails) {

        List<Employee> employeeList = employeeRepository.findAllByOrderByCreatedAtDesc();
        List<EmployeeResponseDto> employeeResponseDtoList = new ArrayList<>();

        for (Employee employee : employeeList) {
            Department department = employee.getDepartment();

            employeeResponseDtoList.add(EmployeeResponseDto.builder()
                    .name(employee.getName())
                    .birth(employee.getBirth())
                    .extension_number(employee.getExtension_number())
                    .mobile_number(employee.getMobile_number())
//                  .enabled(enabledCheck(employee, userDetails))
                    .enabled(enabledCheck(userDetails))
                    .division(department.getDivision().getDivision())
                    .build());
        }
        Employee version = employeeRepository.findTop1ByOrderByModifiedAtDesc();
        String recentVersion = version.getModifiedAt().format((DateTimeFormatter.ofPattern("yyyyMMdd")));

        return EmpResponseDto.version(recentVersion, employeeResponseDtoList);
    }

    //특정 직원 조회
    @Transactional
    public EmpResponseDto<?> getEmployee(Long id) {
        //직원 유무 확인
        Employee employee = isPresentEmployee(id);
        if (null == employee) {
            throw new CustomException(ErrorCode.NOT_EXIST_EMPLOYEE);
        }
        Employee version = employeeRepository.findTop1ByOrderByModifiedAtDesc();
        String recentVersion = version.getModifiedAt().format((DateTimeFormatter.ofPattern("yyyyMMdd")));

        return EmpResponseDto.version(recentVersion,employee);
    }

    //직원 정보 수정
    @Transactional
//    public ResponseDto<?> updateEmp(Long id, EmployeeRequestDto requestDto, HttpServletRequest request) {
    public EmpResponseDto<?> updateEmp(Long id, EmployeeRequestDto requestDto, HttpServletRequest request) {
        // 1. 토큰 유효성 확인
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
//            return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        // 2. 직원 유무 확인
        Employee employee = isPresentEmployee(id);
        if (null == employee) {
//            return ResponseDto.fail(ErrorCode.NOT_EXIST_EMPLOYEE.name(), ErrorCode.NOT_EXIST_EMPLOYEE.getMessage());
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
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
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
        List<EmployeeResponseDto> employeeListResponseDtoList = new ArrayList<>();
        //for문을 통해서 List에 담아주기
        for (Employee employee : employeeList) {
            employeeListResponseDtoList.add(EmployeeResponseDto.builder()
//                            .id(employee.getId())
                    .name(employee.getName()).birth(employee.getBirth()).extension_number(employee.getExtension_number()).mobile_number(employee.getMobile_number())
//                            .enabled(enabledCheck(employee, userDetails))
                    .enabled(enabledCheck(userDetails)).build());
        }
        return ResponseDto.success(employeeListResponseDtoList);
    }

    //============================리더테스트 시작부분========================================================================

    // 리더 선택
    @Transactional
    public ResponseDto<?> LeaderSelect(Long id) {
        // 1. 직원 확인
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
        LinkedList result = new LinkedList();
        obj.put("version", recentVersion);

        for (int i = 0; i < divisionList.size(); i++) {
            String divisionName = divisionList.get(i).getDivision();

            LinkedList divisionObj = new LinkedList();

            for (int j = 0; j < empList.size(); j++) {
                String empDivision = empList.get(j).getDivision();

                if(divisionName.equals(empDivision)) {

                    Map contactObj = new LinkedHashMap();
                    contactObj.put("name", empList.get(j).getName());
                    contactObj.put("mobile_number", empList.get(j).getMobile());
                    contactObj.put("email", empList.get(j).getEmail());

                    Map contactObj2 = new LinkedHashMap();
                    contactObj2.put("department", empList.get(j).getDepartment());
                    contactObj2.put("contact", contactObj);


                    // "R&D": [{ contactObj2 }]
                    divisionObj.add(contactObj2);
                }
            }

            Map contactObj3 = new LinkedHashMap();
            contactObj3.put(divisionName, divisionObj);
            result.add(contactObj3);
        }
        obj.put("division", result);

        return obj;
    }



    //============================리더테스트 끝=========================================================================
    @Transactional
    public Employee isPresentEmployee(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee.orElse(null);
    }

//    @Transactional
//    public Admin validateAdmin(HttpServletRequest request) {
//        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
//            return null;
//        }
//        return tokenProvider.getAdminFromAuthentication();
//    }

//    @Transactional
////    public ResponseDto<?> mainBuisness(HttpServletRequest request) {
//    public ResponseFirstDto mainBuisness() {
//        //1
//        ResponseFirstDto responseFirstDto = new ResponseFirstDto();
//
//        HashMap RnDMap = methodTester("R&D");
//        HashMap salseMap = methodTester("영업");
//
//        ArrayList resultList = new ArrayList<>();
//
//        resultList.add(RnDMap);
//        resultList.add(salseMap);
//
//        responseFirstDto.setDivision(resultList);
//        return responseFirstDto;
//    }
//
//    @Transactional
//    public HashMap methodTester(String paramDivision) {
//
//        //2
//        HashMap<String, List<ResponseSecondDto>> responseSecondDtoMap = new HashMap<>();
//        //3
//        ArrayList<ResponseSecondDto> responseSecondDtos = new ArrayList<>();
//        //4
//        ResponseSecondDto responseSecondDto = new ResponseSecondDto();
//        //5
//        ResponseThirdDto responseThirdDto = new ResponseThirdDto();
//
//        MapperDto mapperDto = new MapperDto();
////        mapperDto = employeeRepository.test11("R&D");
//
//        List<Employee> test = employeeRepository.test(paramDivision);
//
//
//        for (Employee employee : test) {
//                // 1
//                responseThirdDto.setEmail(employee.getEmail());
//                responseThirdDto.setName(employee.getName());
//                responseThirdDto.setMobile_number(employee.getMobile_number());
//
//                // 2
//                responseSecondDto.setDepartment(employee.getDepartment().getDepartment());
//                responseSecondDto.setContact(responseThirdDto);
//                responseSecondDtos.add(responseSecondDto);
//            }
//
//        responseSecondDtoMap.put(paramDivision , responseSecondDtos);
//        return responseSecondDtoMap;
//        }
////        return responseSecondDtoMap;
////    }

    @Transactional
//    public ResponseDto<?> test(String paramDivision) {
    public ResponseFirstDto test(String paramDivision) {
        ResponseFirstDto responseFirstDto = new ResponseFirstDto();

        //2
        HashMap<String, List<ResponseSecondDto>> responseSecondDtoMap = new HashMap<>();
        //3\
        ArrayList<ResponseSecondDto> responseSecondDtos = new ArrayList<>();
        //4
        ResponseSecondDto responseSecondDto = new ResponseSecondDto();
        //5
        ResponseThirdDto responseThirdDto = new ResponseThirdDto();

        MapperDto mapperDto = new MapperDto();
//        mapperDto = employeeRepository.test11("R&D");

        List<Employee> test = employeeRepository.test(paramDivision);


        for (Employee employee : test) {
            // 1
            responseThirdDto.setEmail(employee.getEmail());
            responseThirdDto.setName(employee.getName());
            responseThirdDto.setMobile_number(employee.getMobile_number());

            // 2
            responseSecondDto.setDepartment(employee.getDepartment().getDepartment());
            responseSecondDto.setContact(responseThirdDto);
            responseSecondDtos.add(responseSecondDto);
        }

        responseSecondDtoMap.put(paramDivision, responseSecondDtos);
        ArrayList test1 = new ArrayList();
        test1.add(responseSecondDtoMap);
        responseFirstDto.setDivision(test1);
//        return ResponseDto.success(responseFirstDto);
        return responseFirstDto;
    }

}

//    public class ResponseFirstDto {
//        //    {
//        //        "version": "20230331",
//        //            "division": []
//
//        String version = "20230331";
//        List<HashMap<String , List<ResponseSecondDto>>> division;
//    }

//  public class ResponseSecondDto {
//       String department;
//       ResponseThirdDto contact;
//  }
//     {
//     "department": "AI융합기술연구소",
//     "contact": {
//     "name": "홍길동",
//     "mobile_number": "010",
//     "email": "yusung@everybot.net"
//     }

//    public class ResponseThirdDto {
//        String name;
//        String mobile_number;
//        String email;
//     "name": "홍길동",
//     "mobile_number": "010",
//     "email": "yusung@everybot.net"
//    }
