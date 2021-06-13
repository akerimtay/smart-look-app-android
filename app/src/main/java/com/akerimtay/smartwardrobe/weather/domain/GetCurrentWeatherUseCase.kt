package com.akerimtay.smartwardrobe.weather.domain

import com.akerimtay.smartwardrobe.common.base.UseCaseSync
import com.akerimtay.smartwardrobe.weather.domain.gateway.WeatherLocalGateway
import com.akerimtay.smartwardrobe.weather.model.Weather
import kotlinx.coroutines.flow.Flow

class GetCurrentWeatherUseCase(
    private val weatherLocalGateway: WeatherLocalGateway,
) : UseCaseSync<Unit, Flow<Weather?>>() {
    override fun execute(parameters: Unit): Flow<Weather?> = weatherLocalGateway.getCurrent()
}