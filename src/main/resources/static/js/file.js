var uploadFile = {
    init: function (e) {
        var _this = this;

        _this.saveForm();
        _this.detailForm();
        _this.editForm();


    },

    saveForm : function (){

        $(document).ready(function (e){

            let formObj = $('#post_save_form');

            $('#post_submit').on('click',function (e){
                e.preventDefault();

                console.log("저장버튼클릭")

                let str= "";

                $('.uploadResult li').each(function (i,obj){
                    let jobj = $(obj);
                    console.log(jobj);

                    str +="<input type='hidden' name='uploadFiles["+i+"].fileName' value='"+jobj.data("filename")+"'>";
                    str +="<input type='hidden' name='uploadFiles["+i+"].uuid' value='"+jobj.data("uuid")+"'>";
                    str +="<input type='hidden' name='uploadFiles["+i+"].path' value='"+jobj.data("path")+"'>";
                    str +="<input type='hidden' name='uploadFiles["+i+"].fileType' value='"+jobj.data("filetype")+"'>";


                });

                formObj.append(str).submit();




            })
        })



        let regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
        let maxSize = 5242880;

        function checkExtension(fileName,fileSize){
            if(fileSize >= maxSize){
                alert("파일 사이즈 초과");
                return false;
            }

            if(regex.test(fileName)){
                alert("해당 종류의 파일은 업로드 할 수 없습니다.");
                return false;
            }

            return true;
        }

        const cloneObj = $('.uploadDiv').clone();

        $("#uploadFile").on('change',function (e){

            console.log("파일 변경")



            let uploadResult = $('.uploadResult ul');

            function showUploadFile(uploadResultArr){
                let str ="";

                $(uploadResultArr).each(function (i,obj){
                    if(!obj.fileType){
                        let fileCallPath = encodeURIComponent(obj.path +"/"+ obj.uuid +"_"+obj.fileName);

                        str+='<li data-path="'+obj.path+'" data-uuid="'+obj.uuid+'" data-filename="'+obj.fileName+'" data-filetype="'+obj.fileType+'">' +
                            '<div><a href="/download?fileName='+fileCallPath+'"><img src="../img/default.jpg"> '+obj.fileName+'</a>  '+
                            "<button type='button' data-file=\'"+fileCallPath+"\' data-type='file'> X   </button></div></li>"



                    }else{
                        let fileCallPath = encodeURIComponent(obj.path +"/"+ obj.uuid +"_"+obj.fileName);

                        str += '<li data-path="'+obj.path+'" data-uuid="'+obj.uuid+'" data-filename="'+obj.fileName+'" data-filetype="'+obj.fileType+'">' +
                            '<img src="/display?fileName='+fileCallPath+'" style="width: 100px;height: 100px" >'+
                            "<button type='button'  data-file=\'"+fileCallPath+"\' data-type='image'>X</button></li>";

                    }
                });

                uploadResult.append(str);

            }

            //폼데이터 생성
            let formData = new FormData();
            let inputFile = $("#uploadFile");
            let files =inputFile[0].files;

            console.log(files)

            for(let i=0; i< files.length; i++){

                if(!checkExtension(files[i].name, files[i].size)){
                    return false;
                }

                formData.append("uploadFile",files[i]); //페이지 고침없이 폼데이터 전송 ( uploadFile(key) 에 file데이터(value) 계속 추가
            }




            $.ajax({
                url:'/uploadAjax',
                type:'POST',
                processData:false,
                contentType:false,
                dataType:'json',
                data:formData,
                success:function (data){
                    console.log(data);
                    showUploadFile(data);
                    //    $('.uploadDiv').html(cloneObj.html()); //파일 초기화
                },
                error:function (data){
                    alert('x')
                }
            });

        })


        $('.uploadResult').on('click','button',function (e){
            let targetFile = $(this).data('file');
            let type=$(this).data('type');
            let targetLi = $(this).closest("li");
            console.log('타겟파일',targetFile);

            $.ajax({
                url:'/deleteFile',
                data:{fileName:targetFile,type:type},
                dataType: "text",
                type:'post',
                success:function (data){
                    alert(data)
                    targetLi.remove();
                }
            })

        })

    }, //saveForm end

    detailForm : function (){

        let postId = $('#detail_post_id').val();
        let uploadDetail = $('.uploadDetail ul');

        $.getJSON('/postFile/' + postId, function (data) {


            console.log(data);

                $.each(data, function (idx, obj) {
                    let str ="";

                    if(!obj.fileType){
                        let fileCallPath = encodeURIComponent(obj.path +"/"+ obj.uuid +"_"+obj.fileName);

                        str+='<li data-path="'+obj.path+'" data-uuid="'+obj.uuid+'" data-filename="'+obj.fileName+'" data-filetype="'+obj.fileType+'">' +
                            '<div><a href="/download?fileName='+fileCallPath+'"><img src="../img/default.jpg"> '+obj.fileName+'</a>  '+
                            "</li></div>"



                    }else{
                        let fileCallPath = encodeURIComponent(obj.path +"/"+ obj.uuid +"_"+obj.fileName);

                        str += '<li data-path="'+obj.path+'" data-uuid="'+obj.uuid+'" data-filename="'+obj.fileName+'" data-filetype="'+obj.fileType+'">' +
                            '<img src="/display?fileName='+fileCallPath+'" style="width: 100px;height: 100px" >'+
                            "</li>";

                    }

                    uploadDetail.append(str);
                })


        })

    }, //detailForm end

    editForm : function (){

        let formObj = $('#post_edit_form');

        $('#edit_submit').on('click',function (e){
            e.preventDefault();

            console.log("저장버튼클릭")

            let str= "";

            $('.uploadResultEdit li').each(function (i,obj){
                let jobj = $(obj);
                console.log(jobj);

                str +="<input type='hidden' name='uploadFiles["+i+"].fileName' value='"+jobj.data("filename")+"'>";
                str +="<input type='hidden' name='uploadFiles["+i+"].uuid' value='"+jobj.data("uuid")+"'>";
                str +="<input type='hidden' name='uploadFiles["+i+"].path' value='"+jobj.data("path")+"'>";
                str +="<input type='hidden' name='uploadFiles["+i+"].fileType' value='"+jobj.data("filetype")+"'>";


            });

            formObj.append(str).submit();




        })

        let postId = $('#edit_post_id').val();
        let uploadResultEdit = $('.uploadResultEdit ul');

        $.getJSON('/postFile/' + postId, function (data) {


            console.log(data);

            $.each(data, function (idx, obj) {
                let str ="";

                if(!obj.fileType){
                    let fileCallPath = encodeURIComponent(obj.path +"/"+ obj.uuid +"_"+obj.fileName);

                    str+='<li data-path="'+obj.path+'" data-uuid="'+obj.uuid+'" data-filename="'+obj.fileName+'" data-filetype="'+obj.fileType+'">' +
                        '<div><a href="/download?fileName='+fileCallPath+'"><img src="../img/default.jpg"> '+obj.fileName+'</a>  '+
                        "<button type='button' data-file=\'"+fileCallPath+"\' data-type='file'> X   </button></li></div>"



                }else{
                    let fileCallPath = encodeURIComponent(obj.path +"/"+ obj.uuid +"_"+obj.fileName);

                    str += '<li data-path="'+obj.path+'" data-uuid="'+obj.uuid+'" data-filename="'+obj.fileName+'" data-filetype="'+obj.fileType+'">' +
                        '<img src="/display?fileName='+fileCallPath+'" style="width: 100px;height: 100px" >'+
                        "<button type='button'  data-file=\'"+fileCallPath+"\' data-type='image'>X</button></li>";

                }

                uploadResultEdit.append(str);
            })


        })


        //이미지만 삭제
        $('.uploadResultEdit').on('click','button',function (e){
            let targetLi = $(this).closest("li");
            targetLi.remove();
        })



        let regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
        let maxSize = 5242880;

        function checkExtension(fileName,fileSize){
            if(fileSize >= maxSize){
                alert("파일 사이즈 초과");
                return false;
            }

            if(regex.test(fileName)){
                alert("해당 종류의 파일은 업로드 할 수 없습니다.");
                return false;
            }

            return true;
        }

        const cloneObj = $('.uploadDiv').clone();

        $("#uploadFileEdit").on('change',function (e){



            console.log("파일 변경")



            let uploadResultEdit = $('.uploadResultEdit ul');

            function showUploadFile(uploadResultEditArr){
                let str ="";

                $(uploadResultEditArr).each(function (i,obj){
                    if(!obj.fileType){
                        let fileCallPath = encodeURIComponent(obj.path +"/"+ obj.uuid +"_"+obj.fileName);

                        str+='<li data-path="'+obj.path+'" data-uuid="'+obj.uuid+'" data-filename="'+obj.fileName+'" data-filetype="'+obj.fileType+'">' +
                            '<div><a href="/download?fileName='+fileCallPath+'"><img> '+obj.fileName+'</a>  '+
                            "<button type='button' data-file=\'"+fileCallPath+"\' data-type='file'> X   </button></div></li>"



                    }else{
                        let fileCallPath = encodeURIComponent(obj.path +"/"+ obj.uuid +"_"+obj.fileName);

                        str += '<li data-path="'+obj.path+'" data-uuid="'+obj.uuid+'" data-filename="'+obj.fileName+'" data-filetype="'+obj.fileType+'">' +
                            '<img src="/display?fileName='+fileCallPath+'" style="width: 100px;height: 100px" >'+
                            "<button type='button'  data-file=\'"+fileCallPath+"\' data-type='image'>X</button></li>";

                    }
                });

                uploadResultEdit.append(str);

            }

            //폼데이터 생성
            let formData = new FormData();
            let inputFile = $("#uploadFileEdit");
            let files = inputFile[0].files;

            console.log(files)

            for(let i=0; i< files.length; i++){

                if(!checkExtension(files[i].name, files[i].size)){
                    return false;
                }

                formData.append("uploadFile",files[i]); //페이지 고침없이 폼데이터 전송 ( uploadFile(key) 에 file데이터(value) 계속 추가
            }




            $.ajax({
                url:'/uploadAjax',
                type:'POST',
                processData:false,
                contentType:false,
                dataType:'json',
                data:formData,
                success:function (data){
                    console.log(data);
                    showUploadFile(data);
                    //    $('.uploadDiv').html(cloneObj.html()); //파일 초기화
                },
                error:function (data){
                    alert('x')
                }
            });

        })

    }












};
uploadFile.init()