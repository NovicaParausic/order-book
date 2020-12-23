# order-book
The goal of this project is to implement  fast price/time algorythm for order matching system that I found in [exchange-core library](https://github.com/mzheravin/exchange-core). 
This solution utilzes storing price bucket objects in object pool. Each price bucket contains informations about price, volume and it continas DirectOrder iterator object 
which is responsible for storing information about each order. 
