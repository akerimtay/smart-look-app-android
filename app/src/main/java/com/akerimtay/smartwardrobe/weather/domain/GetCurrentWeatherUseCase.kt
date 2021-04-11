package com.akerimtay.smartwardrobe.weather.domain

import androidx.lifecycle.LiveData
import com.akerimtay.smartwardrobe.common.base.UseCase
import com.akerimtay.smartwardrobe.weather.domain.gateway.WeatherLocalGateway
import com.akerimtay.smartwardrobe.weather.model.Weather
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class GetCurrentWeatherUseCase(
    private val weatherLocalGateway: WeatherLocalGateway
) : UseCase<Unit, LiveData<Weather?>>() {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun execute(parameters: Unit): LiveData<Weather?> = weatherLocalGateway.getCurrent()
}