======================
movie-database-backend
======================

.. image:: https://travis-ci.org/iweinzierl/movie-database-backend.svg?branch=master
   :target: https://travis-ci.org/iweinzierl/movie-database-backend
   :alt: Build Status

Backend application for movie-database. Basically, this project provides a REST API to access the movie data.

Run
---
.. code-block:: bash

   $ mvn package
   $ #mvn package -P production  // build for production (copies resources from src/main/envs/production

   $ java -jar target/moviedatabase-backend-0.0.1-SNAPSHOT.jar
   $ #java -jar target/moviedatabase-backend-0.0.1-SNAPSHOT.jar --server.port=7711  // adapt the server port


License
=======

Copyright 2013-2015 Ingo Weinzierl

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
