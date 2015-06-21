package pl.jug.torun.jugfactor.service.web

import org.json4s._
import org.scalatra.json.JacksonJsonSupport
import org.scalatra.{NotFound, ScalatraServlet}
import pl.jug.torun.jugfactor.service.core._

class AnnotationEventController(annotationEventRepository: AnnotationEventRepository,
                             presentationRepository: PresentationRepository) extends ScalatraServlet with JacksonJsonSupport {

   protected implicit val jsonFormats: Formats = DefaultFormats + Serializers.objectId

   before() {
     contentType = formats("json")
   }


  get("/") {
    contentType="text/html"

    <html>
      <body>AnnotationEvent controller</body>
    </html>
  }

  post("/") {
    val annotationEventInput = parsedBody.extract[AnnotationEventInput]

    val annotationEvent = AnnotationEvent(System.currentTimeMillis(), annotationEventInput.eventType)

    annotationEventRepository.add(annotationEvent)
  }
 }
