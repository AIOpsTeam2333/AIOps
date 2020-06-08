# Log Anomaly Detector

a tiny framework for log anomaly detection

Copyright (c) HAN Qi

## TODO List

- [ ] Design
- [ ] Guide

## Features

### Multiple Data Sources

You can get log data by multiple ways:

- push
  - run the app as a server, other systems are allowed to transfer log data through a RESTful API
- pull 
  - fetch log data from ElasticSearch
  - fetch log data from database, e.g. MySQL and MongoDB

## Directory Structure

> using `tree` command 

<pre>
log-anomaly-detector
</pre>

## Guide

You should make several configurations.

First and foremost, you should choose the way the system runs, i.e., push or pull.

- **server:** see `config/server.yml`

Then, some configurations of pre-processing phase: 
  
- **parsing:** see `config/parsing.yml`

Then, 

- **data loader:** You should implement your own data loader to suit your data
- **model:** see `config/options.yml`
  - The option `num_features` is ...


## Demo

## Acknowledgements

[1] The models come from [logdeep](https://github.com/donglee-afar/logdeep).

[2] The [Drain](https://jiemingzhu.github.io/pub/pjhe_icws2017.pdf) log parser comes from [logparser](https://github.com/rhanqtl/logparser).

[3] Demo data come from [loghub]()

[] https://github.com/AICoE/log-anomaly-detector