📌 Custom ViewModel Explained

In this project, I made my own simple version of a ViewModel — without using the usual Android Jetpack ViewModel.
This helps to understand how ViewModels keep data safe when the screen rotates or the activity restarts, and how you can manage them yourself.

🚀 Why Make a Custom ViewModel?

Normally, Android’s ViewModel keeps your data even when the screen rotates or the activity restarts.
Here, I show how to do this yourself, step-by-step, using:

A global App class that stores ViewModels

A simple place to keep your ViewModel objects

Kotlin’s StateFlow to hold data that changes over time

Cleaning up the ViewModel when the activity is really closed


🏗 How This Project Works
1. App.kt — The Place That Stores ViewModels

The App class works like a safe box. It keeps all your ViewModels in a map using names (keys).

When you ask for a ViewModel with a name, it returns the one stored or creates a new one

When you’re done with a ViewModel, you remove it from the box

class App : Application() {

    val mHashMap = HashMap<String, MyViewModel>()

    fun getViewModel(key: String): MyViewModel{
        if (mHashMap[key] != null){
            return mHashMap[key]!!
        }
        val myViewModel = MyViewModel()
        mHashMap[key] = myViewModel
        return myViewModel
    }

    fun clearViewModel(key: String){
        mHashMap.remove(key)
    }
}

2. MyViewModel.kt — The ViewModel Itself

This is your data holder:

Keeps track of a number counter

Uses StateFlow so the UI can watch changes and update automatically

Has a function to increase the counter

class MyViewModel {


class MyViewModel {

    private val mCounterFlow = MutableStateFlow(0)
    val counterStateFlow :  StateFlow<Int>
        get() = mCounterFlow
    fun increaseCounter(){
        mCounterFlow.value = mCounterFlow.value.plus(1)
    }
}


3. MainActivity.kt — How You Use It
Instead of:

ViewModelProvider(this).get(MyViewModel::class.java)

We do:

viewModel = (application as App).getViewModel("MainActivity") as MyViewModel

This keeps the ViewModel alive even when the Activity is recreated.



♻️ Handling Activity Destroy

When the Activity is finished (not rotating), we clear the ViewModel:

override fun onDestroy() {
    super.onDestroy()
    if (!isChangingConfigurations) {
        (application as App).clearViewModel("MainActivity")
    }
}



Summary

This simple custom ViewModel helps you:

Keep data safe across screen rotations

Let the UI react to data changes

Manage when the ViewModel is created and destroyed

It’s a great way to learn what Android’s official ViewModel does behind the scenes!
