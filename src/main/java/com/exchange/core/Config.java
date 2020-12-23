package com.exchange.core;

import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import exchange.core2.core.ExchangeApi;
import exchange.core2.core.ExchangeCore;
import exchange.core2.core.IEventsHandler;
import exchange.core2.core.SimpleEventsProcessor;
import exchange.core2.core.common.config.ExchangeConfiguration;
import exchange.core2.core.processors.journaling.DummySerializationProcessor;
import exchange.core2.core.processors.journaling.ISerializationProcessor;

@Configuration
public class Config {

	@Bean
	public ExchangeApi exchangeApi() {
		
		SimpleEventsProcessor eventsProcessor = new SimpleEventsProcessor(new IEventsHandler() {
			@Override
			public void tradeEvent(TradeEvent tradeEvent) {
				System.out.println("Trade event: " + tradeEvent);
			}

			@Override
			public void reduceEvent(ReduceEvent reduceEvent) {
				System.out.println("Reduce event: " + reduceEvent);
			}

			@Override
			public void rejectEvent(RejectEvent rejectEvent) {
				System.out.println("Reject event: " + rejectEvent);
			}

			@Override
			public void commandResult(ApiCommandResult commandResult) {
				System.out.println("Command result: " + commandResult);
			}

			@Override
			public void orderBook(OrderBook orderBook) {
				System.out.println("OrderBook event: " + orderBook);
			}
		});

		// default exchange configuration
		ExchangeConfiguration conf = ExchangeConfiguration.defaultBuilder().build();

		// no serialization
		Supplier<ISerializationProcessor> serializationProcessorFactory = () -> DummySerializationProcessor.INSTANCE;

		// build exchange core
		ExchangeCore exchangeCore = ExchangeCore.builder().resultsConsumer(eventsProcessor)
				.serializationProcessorFactory(serializationProcessorFactory).exchangeConfiguration(conf).build();

		// start up disruptor threads
		exchangeCore.startup();

		// get exchange API for publishing commands
		 return exchangeCore.getApi();
	}
}
