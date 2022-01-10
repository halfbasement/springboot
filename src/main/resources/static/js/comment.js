var comment = {
    init: function (e) {
        var _this = this;


        _this.load()

        $('#comment_main_submit').on('click', function () {
            _this.createMain();
            $('#comment_main').val('');
        })



        // subComment modal
        $(document).on('click','.subWrite_btn',function (e){

            $('.subComment_modal').remove();


            // 현재 대댓글의 ID와 부모댓글의 ID 불러옴
            let value = e.target.value.split(",");
            let subCommentId =  value[0];
            let parent = value[1];
            console.log(subCommentId,parent);

            //부모가 없다면 , 부모의 아이디 반환
            function checkId(subCommentId,parent){

                if(parent===null||parent===undefined){
                    return subCommentId;
                }else{
                    return parent;
                }

            }

            var url = $('#detail_post_id').val()

                $.getJSON('/comment/' + url +'/modal', function (data) {

                    //html생성성
                    var str = "";

                    str += '<div class="subComment_modal" >'
                    str += '<div className="mb-4" style="background: white; height: 120px ; height:140px; border: 1px solid black">'
                    str += '<div className="sub_comment_addBox" style="padding: 20px;">'
                    str += '<div  id="comment_sub_author"  value="'+data.memberEmail+'">'+data.memberEmail+'</div>' //밸류 미리 박아둬야함 -> 로그인 정보 (컨트롤러에서 세션정보가져오기)
                    str += '<div><textarea className="form-control" rows="1" style="border: none; width: 100%" id="comment_sub" placeholder="댓글을 남겨보세요"></textarea> </div>' // -> 입력
                    str += '<input type="hidden" id="sub_comment_post_id" value="'+data.postId+'"/>' //밸류 미리 박아둬야함 -> @PathVariable
                    str += '<input type="hidden" id="sub_comment_parent" value="' + checkId(subCommentId, parent) + '"/>'
                    str += '<button type="button" id="comment_sub_submit"  style="float: right; border:none; background:white ">등록</button>'
                    str += '</div>'
                    str += '</div>'
                    str += '</div>'

                    $('#subComment_input'+subCommentId).append(str);



                    $('#comment_sub_submit').on('click', function () {
                        console.log('sex')
                        _this.createSub();
                        $('.subComment_modal').remove();

                    })
                })

        })

    },

    createSub: function () {
        var data = {
            postId: $('#sub_comment_post_id').val(),
            comment: $('#comment_sub').val(),
            memberEmail: $('#comment_sub_author').attr('value'),
            parent:$('#sub_comment_parent').val()
        }


        console.log('subModal',data)


        $.ajax({
            type: 'POST',
            url: '/comment',
            //dataType: 'json', 데이터타입 json으로 하면 null(parent)을 못받아줘서 에러가 뜸
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
                    str += '<p class="card-text">' + fomatDate(main.regDate) + '</p>'
                    str += ' <button class="btn btn-default subWrite_btn"   value="'+main.commentId+'">답글쓰기</button> '
                    str += ' <hr class="my-4">'
                    str += '<div id="subComment_input'+main.commentId+'" >'
                    str += '</div>'


                    //자식이 있으면 자식도 생성
                    $.each(data.sub, function (idx, sub) {


                        if (main.commentId == sub.parent) {


                            str += '<div style="margin-left: 50px; padding: 0px">'
                            str += '<h5 class="card-title">' + sub.memberEmail + '</h5>  '
                            str += '<h6 class="card-subtitle mb-2 text-muted">' + sub.comment + '</h6>'
                            str += '<p class="card-text">' + fomatDate(sub.regDate) + '</p> '
                            str += '<button class="btn btn-default subWrite_btn"   value="'+sub.commentId+','+main.commentId+'">답글쓰기</button> '
                            str += ' <hr class="my-4">'
                            str += '</div>'
                            str += '<div id="subComment_input'+sub.commentId+'" >'
                            str += '</div>'

                        }
                    })

                    str += '</div>'
                    $('.comment_group').html(str);


                })

            })

        }
    },

    createMain: function () {
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