# Demo 1

## Main points

* RestEasy with SSE using _Reactive Streams_ `publishers`


## Run the demo

``bash
cd demo1
mvn compile vertx:run
``

Open http://localhost:9000/webroot/index.html.

The application is stopped with `CTRL+C`.


**IMPORTANT:** while it uses the Vert.x Maven Plugin, it's not required. It's just for the hot reload.

## Points of interest

* The `me.escoffier.reactive_summit.demo1.MyWebResource` class:
  
  * Production of SSE
  * Publisher sent as response to a HTTP request
  
* With HTTPie: `http :9000/neo --stream` to illustrate what is produced by the stream:

```text
$ http :9000/neo --stream
  HTTP/1.1 200 OK
  Content-Type: text/event-stream
  Set-Cookie: vertx-web.session=36226a51191fa92175848425850d39bb; Path=/
  Transfer-Encoding: chunked
  
  
  
  data: sleeping
  
  data: awake
  
  data: sleeping

```   

* You can illustrate the reload by editing the `Neo` class and increase the message frequency.
