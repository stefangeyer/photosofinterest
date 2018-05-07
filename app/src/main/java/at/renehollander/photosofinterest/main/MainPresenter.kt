package at.renehollander.photosofinterest.main

import at.renehollander.photosofinterest.UseCaseHandler
import at.renehollander.photosofinterest.main.domain.usecase.ExampleUseCase
import javax.inject.Inject

class MainPresenter @Inject constructor(
        private val useCaseHandler: UseCaseHandler,
        private val exampleUseCase: ExampleUseCase
) : MainContract.Presenter {

    private var view: MainContract.View? = null

    override fun performSomeAction() {
        this.useCaseHandler.execute(
                this.exampleUseCase,
                ExampleUseCase.RequestValues("Hello World"),
                {
                    this.view?.displaySomething()
                },
                {}
        )
    }

    override fun takeView(view: MainContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }
}