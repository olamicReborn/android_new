package com.maggnet.domain.executor

import io.reactivex.Scheduler

interface PostExecutionThread {
    fun scheduler(): Scheduler
}