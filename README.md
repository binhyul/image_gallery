# image_gallery

세로형 목록 화면 | 가로형 목록 화면
---| ---|
![Screenshot_20230712_223216_image-gallery](https://github.com/binhyul/image_gallery/assets/33985795/79ac47f5-4d31-4ae4-92c4-7fef2ab2b3e8) | ![Screenshot_20230712_223225_image-gallery](https://github.com/binhyul/image_gallery/assets/33985795/915c5f91-2527-4fcf-a7ea-209b371a7872)


상세 페이지 로딩(로티) | 상세 페이지 화면
---| ---|
![Screenshot_20230712_223232_image-gallery](https://github.com/binhyul/image_gallery/assets/33985795/28bb247a-8607-48f9-ab54-7641a3365d00)| ![Screenshot_20230712_223234_image-gallery](https://github.com/binhyul/image_gallery/assets/33985795/4c968aa6-9d9e-4d49-a9e6-252640a97933)

사용 라이브러리 : navigation, paging3, retrofit, glide, hilt, lottie 활용하여 개발하였습니다.

전체적인 구조는 MVVM + Clean Architecture 입니다.
Data 레이어는 Repository 패턴 채택하여 개발했습니다.
Domain 레이어에서 UseCase 와 domain data model 을 따로 설정해주었습니다.

세로형 목록 화면은 Paging3, Flow, Coroutine, StateFlow, SharedFlow 활용하여 개발하였습니다.
가로형 목록 화면은 RecyclerView Scroll Listener 를 사용하여 구현하였습니다. 이외는 세로형 목록 화면과 같습니다.
상세 화면은 주어진 Lottie 애니메이션이 종료되면 화면이 나오도록 개발하였습니다. ( 애니메이션이 길어 3배속 처리하였습니다 )



Folder Structure
============================

    - app
       ├── data                   # api service, response model, data source, repository
       ├── domain                 # model, use case, repository interface
       ├── ui                     # activity, fragment, view model, side effect model
       └── README.md
    
    - component                  # custom view module

   
