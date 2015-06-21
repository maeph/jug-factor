package pl.jug.torun.jugfactor.service.web

import org.bson.types.ObjectId
import org.json4s._
import org.scalatra.json.JacksonJsonSupport
import org.scalatra.{BadRequest, NotFound, Ok, ScalatraServlet}
import pl.jug.torun.jugfactor.service.core.{PresentationOutput, Presentation, PresentationRepository, AnnotationEventRepository}

class PresentationController(annotationEventRepository: AnnotationEventRepository,
                             presentationRepository: PresentationRepository) extends ScalatraServlet with JacksonJsonSupport {

   protected implicit val jsonFormats: Formats = DefaultFormats + Serializers.objectId

   before() {
     contentType = formats("json")
   }


  get("/") {
    contentType="text/html"

    <html>
      <body>Presentation controller</body>
    </html>
  }

   get("/:id") {
     val id = new org.bson.types.ObjectId(params("id"));
     presentationRepository.byId(id) match {
       case Some(presentation) => {
         val startTime = presentation.startTime
         val endTime = presentation.startTime + presentation.duration * 1000
         log("startTime: " + startTime)
         log("endTime: " + endTime)

         val annotations = annotationEventRepository.all().filter(p => p.timestamp >= startTime && p.timestamp <= endTime).toList;

         PresentationOutput(id, presentation.title, presentation.url, presentation.startTime, presentation.duration,
         annotations);
       }
       case None => NotFound(s"Location not found: ${params("id")}")
     }
   }



  post("/") {
    val presentation = parsedBody.extract[Presentation]

    presentationRepository.add(presentation)
  }
 }
