// $(document).ready(function() {
//     $('#signup-form').submit(function(event) {
//         // 폼 제출을 막음
//         event.preventDefault();
//
//         // 폼 데이터 수집
//         let formData = {
//             'email': $('input[name=email]').val(),
//             'password': $('input[name=password]').val(),
//             'confirm-password': $('input[name=confirm-password]').val()
//         };
//
//         // AJAX 요청
//         $.ajax({
//             type: 'POST',
//             url: '/api/members/signup',
//             data: formData,
//             dataType: 'json',
//             encode: true
//         })
//             .done(function(data) {
//                 // API 응답 처리
//                 $('#signup-message').text(data.message);
//             })
//             .fail(function(jqXHR, textStatus, errorThrown) {
//                 // API 요청 실패 처리
//                 console.log(jqXHR);
//                 $('#signup-message').text('회원가입에 실패했습니다.');
//             });
//     });
// });