var file = {
    init: function (e) {
        var _this = this;

        _this.submitDefault();


        _this.fileChange();
    },

    submitDefault : function (){

        $(document).ready(function (e){

            $('#post_submit').on('click',function (e){
                e.preventDefault();

                console.log("저장버튼클릭")



            })
        })
    },

    fileChange : function (){

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


        $("#uploadFile").on('change',function (e){

            console.log("파일 변경")

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
                    console.log(data)
                },
                error:function (data){
                    alert('x')
                }
            });

        })



    }










};
file.init()