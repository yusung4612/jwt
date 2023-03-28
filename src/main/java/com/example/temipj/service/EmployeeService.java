package com.example.temipj.service;


import com.example.temipj.domain.employee.Department;
import com.example.temipj.domain.employee.Employee;
import com.example.temipj.domain.admin.Admin;
import com.example.temipj.domain.UserDetailsImpl;
import com.example.temipj.dto.requestDto.EmployeeRequestDto;
import com.example.temipj.dto.responseDto.EmpResponseDto;
import com.example.temipj.dto.responseDto.EmployeeResponseDto;
import com.example.temipj.dto.responseDto.ResponseDto;
import com.example.temipj.dto.responseDto.TestDto.MapperDto;
import com.example.temipj.dto.responseDto.TestDto.ResponseFirstDto;
import com.example.temipj.dto.responseDto.TestDto.ResponseSecondDto;
import com.example.temipj.dto.responseDto.TestDto.ResponseThirdDto;
import com.example.temipj.exception.CustomException;
import com.example.temipj.exception.ErrorCode;
import com.example.temipj.jwt.TokenProvider;
import com.example.temipj.repository.DepartmentRepository;
import com.example.temipj.repository.EmployeeRepository;
import com.example.temipj.repository.LeaderRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

//@Builder
@RequiredArgsConstructor
@Service
public class EmployeeService {

//    Map<Long, Employee> map = new HashMap<>();

    private final EmployeeRepository employeeRepository;

    private final TokenProvider tokenProvider;

    private final LeaderRepository leaderRepository;

    private final DepartmentRepository departmentRepository;

    //직원 등록
//    @Transactional
//    public EmpResponseDto<EmployeeResponseDto> createEmp(EmployeeRequestDto requestDto, HttpServletRequest request) {
//        // 1. 토큰 유효성 확인
//        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
////            return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());
//            throw new CustomException(ErrorCode.INVALID_TOKEN);
//        }
//        // 2. tokenProvider Class의 SecurityContextHolder에 저장된 Member 정보 확인
//        Admin admin = (Admin) tokenProvider.getAdminFromAuthentication();
//        if (null == admin) {
////            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND.name(), ErrorCode.MEMBER_NOT_FOUND.getMessage());
//            throw new CustomException(ErrorCode.ADMIN_NOT_FOUND);
//        }
//        // 3. 등록
//        if (requestDto.getName().isEmpty())
////            return ResponseDto.fail(ErrorCode.NOT_BLANK_NAME.name(), ErrorCode.NOT_BLANK_NAME.getMessage());
//            throw new CustomException(ErrorCode.NOT_BLANK_NAME);
//
//        Employee employee = Employee.builder()
////                .admin(admin)
//                .name(requestDto.getName())
//                .birth(requestDto.getBirth())
//                .extension_number(requestDto.getExtension_number())
//                .mobile_number(requestDto.getMobile_number())
//                .email(requestDto.getEmail())
//                .leader("false")
//                .build();
//        System.out.println("employee = " + employee.getLeader());
//
//        employeeRepository.save(employee);
//
//        return EmpResponseDto.version(
//                EmployeeResponseDto.builder()
////                        .id(employee.getId())
//                        .name(employee.getName())
//                        .birth(employee.getBirth())
//                        .extension_number(employee.getExtension_number())
//                        .mobile_number(employee.getMobile_number())
////                        .email(employee.getEmail())
////                        .department(employee.getDepartment())
//                        .build());
//    }

    //직원 등록 Service
    @Transactional
    public EmpResponseDto createEmp(String departmentId  , EmployeeRequestDto requestDto, HttpServletRequest request) {
// 1. 토큰 유효성 확인
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
// return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
// 2. tokenProvider Class의 SecurityContextHolder에 저장된 Member 정보 확인
        Admin admin = (Admin) tokenProvider.getAdminFromAuthentication();
        if (null == admin) {
// return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND.name(), ErrorCode.MEMBER_NOT_FOUND.getMessage());
            throw new CustomException(ErrorCode.ADMIN_NOT_FOUND);
        }
// 3. 등록
        if (requestDto.getName().isEmpty())
// return ResponseDto.fail(ErrorCode.NOT_BLANK_NAME.name(), ErrorCode.NOT_BLANK_NAME.getMessage());
            throw new CustomException(ErrorCode.NOT_BLANK_NAME);

//FIXME : 샘플코드 확인부1
        Department department = departmentRepository.findById(departmentId);

//FIXME : 샘플코드 확인부2
        Employee employee = Employee.builder()
// .admin(admin)
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

        return EmpResponseDto.version(
                EmployeeResponseDto.builder()
// .id(employee.getId())
                        .name(employee.getName())
                        .birth(employee.getBirth())
                        .extension_number(employee.getExtension_number())
                        .mobile_number(employee.getMobile_number())
// .email(employee.getEmail())
// .department(employee.getDepartment())
                        .build());
    }


    //직원별 enabled 체크
    @Transactional
    public String enabledCheck(Employee employee, UserDetailsImpl userDetails) {
        if (userDetails == null) {
            return "1";
        }
        boolean isCheckedLeader = leaderRepository.existsByAdminAndEmployee(userDetails.getAdmin(), employee);

//        return isCheckedLeader ? "1" : "0"
        return "1";
    }

    //전체 직원 조회
    @Transactional
    public EmpResponseDto<?> getEmployeeAll(UserDetailsImpl userDetails) {

        List<Employee> employeeList = employeeRepository.findAllByOrderByCreatedAtDesc();
        List<EmployeeResponseDto> employeeResponseDtoList = new ArrayList<>();

        for (Employee employee : employeeList) {
            Department department = employee.getDepartment();

            employeeResponseDtoList.add(
                    EmployeeResponseDto.builder()
                            .name(employee.getName())
                            .birth(employee.getBirth())
                            .extension_number(employee.getExtension_number())
                            .mobile_number(employee.getMobile_number())
                            .enabled(enabledCheck(employee, userDetails))
                            .division(department.getDivision().getDivision())
                            .build());
        }
        return EmpResponseDto.version(employeeResponseDtoList);
    }

    //특정 직원 조회
    @Transactional
    public EmpResponseDto<?> getEmployee(Long id) {
        //직원 유무 확인
        Employee employee = isPresentEmployee(id);
        if (null == employee) {
            throw new CustomException(ErrorCode.NOT_EXIST_EMPLOYEE);
        }
        return EmpResponseDto.version(employee);
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
        // 3. tokenProvider Class의 SecurityContextHolder에 저장된 Member 정보 확인
        Admin admin = (Admin) tokenProvider.getAdminFromAuthentication();
//        if (employee.validateAdmin(admin)) {
////            return ResponseDto.fail(ErrorCode.EMPLOYEE_UPDATE_WRONG_ACCESS.name(), ErrorCode.EMPLOYEE_UPDATE_WRONG_ACCESS.getMessage());
//            throw new CustomException(ErrorCode.EMPLOYEE_UPDATE_WRONG_ACCESS);
//        }
        // 4. 수정
        employee.update(requestDto);
//        return ResponseDto.version(employee);
        return EmpResponseDto.version(employee);
    }

    //직원 삭제
//    public ResponseDto<?> deleteEmp(Long id, HttpServletRequest request) {
    public EmpResponseDto<?> deleteEmp(Long id, HttpServletRequest request) {

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
        // 3. SecurityContextHolder에 저장된 Member 확인
        Admin admin = (Admin) tokenProvider.getAdminFromAuthentication();
//        if (employee.validateAdmin(admin)) {
////            return ResponseDto.fail(ErrorCode.EMPLOYEE_UPDATE_WRONG_ACCESS.name(), ErrorCode.EMPLOYEE_UPDATE_WRONG_ACCESS.getMessage());
//            throw new CustomException(ErrorCode.EMPLOYEE_UPDATE_WRONG_ACCESS);
//        }
        // 4. 삭제
        employeeRepository.delete(employee);
//        return ResponseDto.version("해당 직원이 삭제되었습니다.");
        return EmpResponseDto.version("해당 직원이 삭제되었습니다.");
    }

    //직원 검색
    @Transactional
    public ResponseDto<?> searchEmployee(String keyword, UserDetailsImpl userDetails) {
        List<Employee> employeeList = employeeRepository.searchEmp(keyword);
        // 검색된 항목 담아줄 리스트 생성
        List<EmployeeResponseDto> employeeListResponseDtoList = new ArrayList<>();
        //for문을 통해서 List에 담아주기
        for (Employee employee : employeeList) {
            employeeListResponseDtoList.add(
                    EmployeeResponseDto.builder()
//                            .id(employee.getId())
                            .name(employee.getName())
                            .birth(employee.getBirth())
                            .extension_number(employee.getExtension_number())
                            .mobile_number(employee.getMobile_number())
                            .enabled(enabledCheck(employee, userDetails))
                            .build()
            );
        }
        return ResponseDto.success(employeeListResponseDtoList);
    }


    @Transactional
    public Employee isPresentEmployee(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee.orElse(null);
    }

    @Transactional
    public Admin validateAdmin(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getAdminFromAuthentication();
    }

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

        responseSecondDtoMap.put(paramDivision , responseSecondDtos);
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
//     "department": "AI\uc735\ud569\uae30\uc220\uc5f0\uad6c\uc18c",
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
