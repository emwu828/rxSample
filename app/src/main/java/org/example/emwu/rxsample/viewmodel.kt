//package org.example.emwu.rxsample
//
//import io.reactivex.Observable
//import io.reactivex.Observer
//import io.reactivex.disposables.Disposable
//import io.reactivex.functions.Predicate
//import org.example.emwu.rxsample.Model.DataSource
//import org.example.emwu.rxsample.Model.Task
//import timber.log.Timber
//
//class viewmodel {
//
//    private fun createTaskObservable(): Observable<Task> {
//        return Observable
//            .fromIterable(DataSource.createTasksList())
//            .filter( Predicate {
//                try {
//                    Thread.sleep(1000)
//                } catch (e: InterruptedException) {
//                    Timber.e(e)
//                    e.printStackTrace()
//                }
//                Timber.d("Thread: ${Thread.currentThread().name} Task: ${it.description} is ${it.isComplete}")
//                it.isComplete
//            })
//    }
//
//    // executes on main thread
//    private fun createTaskObserver(): Observer<Task> {
//        taskObserver = object : Observer<Task> {
//            override fun onSubscribe(d: Disposable) { // returns void if onSubscribe is implemented, returns disposable if not
//                taskDisposable.add(d)
//            }
//            override fun onError(e: Throwable) {}
//            override fun onComplete() {}
//            override fun onNext(t: Task) {
//                Timber.d("Thread: ${Thread.currentThread().name} Task: ${t.description}")
//                text.text = t.description
//            }
//        }
//        return taskObserver
//    }
//}