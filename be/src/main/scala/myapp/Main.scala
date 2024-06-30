package myapp

import myapp.Model.*
import zio.*
import zio.http.*
import zio.http.Middleware.CorsConfig
import zio.http.codec.*
import zio.http.codec.PathCodec.*
import zio.http.endpoint.*

object Main extends ZIOAppDefault:
  // DB
  var dataList: DataList = List(DataItem(), DataItem(), DataItem())

  // Endpoints
  val getData =
    Endpoint(Method.GET / "data").out[DataList].implement(Handler.succeed(dataList))
  val postData =
    Endpoint(Method.POST / "data").in[DataList].out[Unit].implement(Handler.fromFunction[DataList](dataList = _))

  // App with CORS open
  val app = Routes(getData, postData).toHttpApp @@
    Middleware.cors(CorsConfig().copy(allowedOrigin = _ => Option(Header.AccessControlAllowOrigin.All)))

  override val run: ZIO[Any, Throwable, Nothing] = Server.serve(app).provide(Server.defaultWithPort(9000))
end Main