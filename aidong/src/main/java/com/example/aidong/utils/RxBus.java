package com.example.aidong.utils;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;


    public class RxBus {
        private final Subject<Object, Object> _bus;
        private static class RxBusHolder{
            private static final RxBus instance = new RxBus();
        }

        private RxBus() {
            _bus = new SerializedSubject<>(PublishSubject.create());
        }

        public static synchronized RxBus getInstance() {
            return RxBusHolder.instance;
        }

        public void post(Object o) {
            _bus.onNext(o);
        }

        public <T> Observable<T> toObserverable(Class<T> eventType) {
            return _bus.ofType(eventType);
        }
    }


