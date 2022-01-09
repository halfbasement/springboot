var comment = {
    init: function (e) {
        var _this = this;


        _this.load()

        $('#comment_main_submit').on('click', function () {
            _this.create();
            $('#comment_main').val('');
        })

        $(document).on('click','.subWrite_btn',function (e){

            console.log(e.target.value);
        })

    },

    load: function () {

        function fomatDate(regDate){
            var date = new Date(regDate);

            return date.getFullYear() + '/' +
                (date.getMonth() +1 ) + '/' +
                date.getDate() + ' ' +
                date.getHours() + ':' +
                date.getMinutes();
        }

        var url = $('#detail_post_id').val()

        if (url) {

            $.getJSON('/comment/' + url, function (data) {
                console.log(data);

                let str = "";

                // 댓글 생성
                $.each(data.main, function (idx, main) {



                    str += '<div class="card-body" style="padding: 0px">'
                    str += '<h5 class="card-title">' + main.memberEmail + '</h5>  '
                    str += '<h6 class="card-subtitle mb-2 text-muted">' + main.comment + '</h6>'
                    str += '<p class="card-text">' + fomatDate(main.regDate) + '</p> <button class="btn btn-default subWrite_btn" value="'+main.commentId+'">답글쓰기</button> '
                    str += ' <hr class="my-4">'


                    //자식이 있으면 자식도 생성
                    $.each(data.sub, function (idx, sub) {


                        if (main.commentId == sub.parent) {


                            str += '<div style="margin-left: 50px; padding: 0px">'
                            str += '<h5 class="card-title">' + sub.memberEmail + '</h5>  '
                            str += '<h6 class="card-subtitle mb-2 text-muted">' + sub.comment + '</h6>'
                            str += '<p class="card-text">' + fomatDate(sub.regDate) + '</p> <button class="btn btn-default subWrite_btn" value="'+sub.parent+'">답글쓰기</button> '
                            str += ' <hr class="my-4">'
                            str += '</div>'

                        }
                    })

                    str += '</div>'
                    $('.comment_group').html(str);


                })

            })

        }
    },

    create: function () {
        var data = {
            postId: $('#comment_post_id').val(),
            comment: $('#comment_main').val(),
            memberEmail: $('#comment_main_author').attr('value'),
        }

        var url = $('#detail_post_id').val()

        $.ajax({
            type: 'POST',
            url: '/comment',
            //dataType: 'json', 데이터타입 json으로 하면 널을 못받아줘서 에러가 뜸
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            success: function (data) {
            },
            error: function (e) {
                alert(JSON.stringify(e))
            }
        }).done(function (){
            comment.load();
        })


    }
    ,


};
comment.init()