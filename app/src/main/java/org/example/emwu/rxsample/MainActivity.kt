package org.example.emwu.rxsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import org.example.emwu.rxsample.Model.DataSource
import org.example.emwu.rxsample.Model.Task
import org.reactivestreams.Subscription
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    @BindView(R.id.seekBar)
    lateinit var seekBar: SeekBar

    @BindView(R.id.text)
    lateinit var text: TextView

    private lateinit var taskObservable: Observable<Task>
    private lateinit var taskObserver: Observer<Task>
    private var taskDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        taskObservable = createTaskObservable()
        taskObserver = createTaskObserver()


        val taskSubscription = taskObservable
            .subscribeOn(Schedulers.io())       //observable will run on IO thread.
            .observeOn(AndroidSchedulers.mainThread())      //Observer will run on main thread.
            .subscribe(taskObserver)               //subscribe the observer
    }

    // in MVVM place within onCleared method
    override fun onDestroy() {
        super.onDestroy()
        if (taskDisposable != null) taskDisposable.clear() // allow re subscription
        if (taskDisposable != null) taskDisposable.dispose() // hard clear, can't re subscribe
    }

    /*
    * Converts an Iterable sequence into an ObservableSource that emits the items in the sequence.
    * Define background thread to do work on
    * Define which thread to observe on
    */
    private fun createTaskObservable(): Observable<Task> {
        return Observable
            .fromIterable(DataSource.createTasksList())
            .filter( Predicate {
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    Timber.e(e)
                    e.printStackTrace()
                }
                Timber.d("Thread: ${Thread.currentThread().name} Task: ${it.description} is ${it.isComplete}")
                it.isComplete
            })
    }

    // executes on main thread
    private fun createTaskObserver(): Observer<Task> {
        taskObserver = object : Observer<Task> {
            override fun onSubscribe(d: Disposable) { // returns void if onSubscribe is implemented, returns disposable if not
                taskDisposable.add(d)
            }
            override fun onError(e: Throwable) {}
            override fun onComplete() {}
            override fun onNext(t: Task) {
                Timber.d("Thread: ${Thread.currentThread().name} Task: ${t.description}")
                text.text = t.description
            }
        }
        return taskObserver
    }
}

