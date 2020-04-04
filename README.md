# vantageProCodeRepo

## Key:
 :key: = important

:squirrel: = important design decisions
 

## What we're doing:
You are going to develop the core software for the [Wireless Vantage Pro2 Integrated Sensor Suite](https://www.davisinstruments.com/product/wireless-vantage-pro2-integrated-sensor-suite/) (ISS)
(Product number: 6322) manufactured by [Davis Instruments](https://www.davisinstruments.com/).

## What should this software we make do?

- collect data measurements from all attached sensors of the device according to the device specifications (provided on the website), 
- process the sensor data properly to serve (or send) it to the data monitoring devices. 
  - These include [Wireless Vantage Pro2 Console Receivers](https://www.davisinstruments.com/product/vantage-pro2-consolereceiver/?_ga=2.16052982.1932156901.1585547857-930968521.1585547857) (Product number: 6316) and [Wireless Weather Envoy](https://www.davisinstruments.com/product/wireless-weather-envoy/) (Product number: 6316) that transfers the incoming data to a stand-alone weather monitoring application running on your computer.
- Transfer the data between the weather station and receiver or envoy devices on a (wireless) IP network
by serializing the data.

## What features does this Wireless Vantage Pro2™ ISS Product number:6322 possess?
- :key: Detachable _anemometer_ provides wind speed and direction and can be sited up to 40 ft. (12 m) above the other sensors.
- :key: _rain collector_
- :key: _temperature and humidity sensors_ 

## Useful information from: Vantage Pro2 Wireless Stations Specification Sheet.pdf

#### Page 1:

- Wind sensor cable length
 - Cable Length, Anemometer: 40 feet (12 m) (included) 240 feet (73 m) (maximum recommended)
 - Note: Maximum displayable wind decreases as the length of cable increases. At 140’ (42 m) of cable, the maximum wind speed displayed is 135 mph (60 m/s); at 240’ (73 m), the maximum wind speed displayed is 100 mph (34 m/s).
   -  :squirrel: When calculating the max wind speed for our random input generator, we need to factor in the cable length as a field.
   
 - Rain Collector Type: Tipping spoon, 0.01" per tip for US versions, 0.2 mm for metric versions, 33.2 in^2 (214 cm ) collection area
