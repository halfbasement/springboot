var comment = {
    init: function (e) {
        var _this = this;


        _this.load()

        $('#comment_main_submit').on('click', function () {
            _this.create();
            $('#comment_main').val('');
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



                    str += '<div class="card-body">'
                    str += '<h5 class="card-title">' + main.memberEmail + '</h5>  '
                    str += '<h6 class="card-subtitle mb-2 text-muted">' + main.comment + '</h6>'
                    str += '<p class="card-text">' + fomatDate(main.regDate) + '</p> '
                    str += '</div>'
                    //자식이 있으면 자식도 생성
                    $.each(data.sub, function (idx, sub) {


                        if (main.commentId == sub.parent) {


                            str += '<div class="card-body" style="margin-left: 50px">'
                            str += '<h5 class="card-title">' + sub.memberEmail + '</h5>  '
                            str += '<h6 class="card-subtitle mb-2 text-muted">' + sub.comment + '</h6>'
                            str += '<p class="card-text">' + fomatDate(sub.regDate) + '</p> '
                            str += '</div>'
                        }
                    })


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

        console.log('data', data);


        $.ajax({
            type: 'POST',
            url: '/comment',
            //dataType: 'json', 데이터타입 json으로 하면 널을 못받아줘서 에러가 뜸
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            success: function (data) {
                alert(data)
            },
            error: function (e) {
                alert(JSON.stringify(e))
            }
        })


    }
    ,
    update: function () {
        var data = {
            postNo: $('#update-post-no').val(),
            postTitle: $('#update-post-title').val(),
            postContent: $('#update-post-content').val()
        }

        console.log(data)
        $.ajax({
            type: 'PUT',
            url: '/post/' + data.postNo,
            // dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert("수정 되었습니다")
            window.location.href = '/post';

        }).fail(function (e) {
            alert(JSON.stringify(e))
        });
    },


    delete: function () { //넘어갈 값이 없어서 id값으로만
        var id = $('#detail-post-no').val();

        $.ajax({
            type: 'DELETE',
            url: '/post/' + id,
            // dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(id)
        }).done(function () {
            alert("삭제 되었습니디") //일로 오기전에 요청페이지를 들어가서 done이 되기 때문에 현재 오류 뜸
            window.location.href = '/post';

        }).fail(function (e) {
            alert(JSON.stringify(e))
        });
    },


};
comment.init()