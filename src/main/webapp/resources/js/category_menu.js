    // <script> 태그는 제외하고 작성
    $(document).ready(function() {

      // 1차카테고리 선택시
      $("div#category_menu li a").on("mouseover", function(e) {
        e.preventDefault();
        // console.log("1차카테고리 over");

        let sel_first_category = $(this);
        let cg_code = $(this).data("cg_code");
        // console.log("1차카테고리코드 : ", cg_code);
        // return;

        // let url = '2차카테고리 정보를 가져오는 주소';
        let url = '/category/secondCategory/' + cg_code;
        $.getJSON(url, function(category) {

          // console.log(category);
          let str = '<ul class="nav justify-content-center" id="second_category">';

          for(let i=0; i<category.length; i++) {
            str += '<li class="nav-item">';
            str += '<a class="nav-link active" href="#" data-cg_code="' + category[i].cg_code + '">' + category[i].cg_name + '</a>';
            str += '</li>';
          }

          str += '</ul>';

          // console.log(str);
          sel_first_category.parent().parent().next().remove();
          sel_first_category.parent().parent().after(str);
        });
      });

      // 2차카테고리 선택
      // 동적으로 생성된 태그를 JQuery에서 직접 선택자로 접근 불가능
      /*
      $("정적태그 참조 선택자").on("이벤트명", "동적태그를 참조 선택자", function(e) {

      })
      */
      $("div#category_menu").on("click", "ul#second_category", function(e) {
        console.log("2차카테고리작업")
      });


    });
