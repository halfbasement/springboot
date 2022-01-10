var comment = {
    init: function (e) {
        var _this = this;


        _this.load()

        $('#comment_main_submit').on('click', function () {
            _this.createMain();
            $('#comment_main').val('');
        })



        $(document).on('click','.comment_delete_btn',function (e){
            let commentId =  e.target.value;
            let deleteUrl = '/comment/'+commentId;




            $.ajax({
                type: 'DELETE',
                url: deleteUrl,
                //dataType: 'json', 데이터타입 json으로 하면 null(parent)을 못받아줘서 에러가 뜸
                contentType: 'application/json; charset=utf-8',
                success: function (data) {
                    alert('삭제 완료')
                },
                error: function (e) {
                    alert(JSON.stringify(e))
                }
            }).done(function (){
                comment.load();
            })

        })

        $(document).on('click','.comment_update_btn',function (e){


        })


        // subComment modal On/off
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

            let postId = $('#detail_post_id').val()

            let url='/comment/'+postId+'/addModalInfo'
                $.getJSON(url, function (data) {

                    console.log('답글쓰기 data',data)

                    if(data) {
                        //html생성
                        var str = "";

                        str += '<div class="subComment_modal" >'
                        str += '<div className="mb-4" style="background: white; height: 120px ; height:140px; border: 1px solid black">'
                        str += '<div className="sub_comment_addBox" style="padding: 20px;">'
                        str += '<div  id="comment_sub_author"  value="' + data.memberEmail + '">' + data.memberEmail + '</div>' //밸류 미리 박아둬야함 -> 로그인 정보 (컨트롤러에서 세션정보가져오기)
                        str += '<div><textarea className="form-control" rows="1" style="border: none; width: 100%" id="comment_sub" placeholder="댓글을 남겨보세요"></textarea> </div>' // -> 입력
                        str += '<input type="hidden" id="sub_comment_post_id" value="' + data.postId + '"/>' //밸류 미리 박아둬야함 -> @PathVariable
                        str += '<input type="hidden" id="sub_comment_parent" value="' + checkId(subCommentId, parent) + '"/>'
                        str += '<button type="button" id="comment_sub_cancel"  style="float: right; border:none; background:white ">취소</button>'
                        str += '<button type="button" id="comment_sub_submit"  style="float: right; border:none; background:white ">등록</button>'
                        str += '</div>'
                        str += '</div>'
                        str += ' <hr class="my-4">'
                        str += '</div>'


                        $('#subComment_input' + subCommentId).append(str);

                    }

                        $('#comment_sub_submit').on('click', function () {
                            _this.createSub();
                            $('.subComment_modal').remove();

                        })

                        $('#comment_sub_cancel').on('click',function (){
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

        function fomatDate(modifiedDate){
            var date = new Date(modifiedDate);

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

                    function checkMainEmail(){


                        if( data.memberEmail!==false && data.memberEmail === main.memberEmail){
                            return ' <button class="btn btn-danger comment_delete_btn" style="float: right" value="'+main.commentId+'" >삭제</button> '+
                                   ' <button class="btn btn-primary comment_update_btn" style="float: right"  value="'+main.commentId+'">수정</button>';
                        }else{
                            return '';
                        }
                    }



                    str += '<div class="card-body" style="padding: 0px">'
                    str += '<h5 class="card-title">' + main.memberEmail + '</h5>  '
                    str += '<h6 class="card-subtitle mb-2 text-muted">' + main.comment + '</h6>'
                    str += '<p class="card-text">' + fomatDate(main.modifiedDate) + '</p>'
                    str += ' <button class="btn btn-default subWrite_btn"   value="'+main.commentId+'">답글쓰기</button> '
                    str += checkMainEmail();
                    str += ' <hr class="my-4">'
                    str += '<div id="subComment_input'+main.commentId+'" >'
                    str += '</div>'


                    //자식이 있으면 자식도 생성
                    $.each(data.sub, function (idx, sub) {

                        function checkSubEmail(){
                            if( data.memberEmail !== false  && data.memberEmail === sub.memberEmail){
                                return ' <button class="btn btn-danger comment_delete_btn" style="float: right" value="'+sub.commentId+'">삭제</button> '+
                                       ' <button class="btn btn-primary comment_update_btn" style="float: right" value="'+sub.commentId+'">수정</button>';
                            }else{
                                return '';
                            }
                        }

                        if (main.commentId == sub.parent) {


                            str += '<div style="margin-left: 50px; padding: 0px">'
                            str += '<h5 class="card-title">' + sub.memberEmail + '</h5>  '
                            str += '<h6 class="card-subtitle mb-2 text-muted">' + sub.comment + '</h6>'
                            str += '<p class="card-text">' + fomatDate(sub.modifiedDate) + '</p> '
                            str += '<button class="btn btn-default subWrite_btn"   value="'+sub.commentId+','+main.commentId+'">답글쓰기</button> '
                            str += checkSubEmail();
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