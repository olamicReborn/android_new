package com.maggnet.domain.usecase

interface UseCaseListener {

    fun onPreExecute()

    fun onPostExecute()
}