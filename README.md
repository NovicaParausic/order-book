# order-book

The goal of this project is to implement fast price/time algorithm for order matching system that I found in [exchange-core library](https://github.com/mzheravin/exchange-core). This solution utilizes storing price bucket objects in object pool. Each price bucket contains information about price, volume and it contains DirectOrder iterator object which is responsible for storing information about each order.
