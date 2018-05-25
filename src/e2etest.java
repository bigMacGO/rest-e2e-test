import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class e2etest {

    @Test
    public void getTest(){

        RestAssured.baseURI="https://jsonplaceholder.typicode.com";

        given().
                when().
                get("/photos").
                then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
                body("[777].id",Matchers.is(778));

    }

    @Test
    public void postTest(){

        RestAssured.baseURI="https://jsonplaceholder.typicode.com";

        given().
                body("{\n" +
                        "\"albumId\": 101,\n" +
                        "\"title\": \"ini pini voluptate sit officia non nesciunt quis\",\n" +
                        "\"url\": \"http://placehold.it/600/1b9d08\"\n" +
                        "}").
                contentType(ContentType.JSON).
                when().
                post("/photos").
                then().statusCode(201).and().contentType(ContentType.JSON).and().
                body("albumId",Matchers.is(101)).
                body("title",Matchers.equalTo("ini pini voluptate sit officia non nesciunt quis")).
                body("url",Matchers.equalTo("http://placehold.it/600/1b9d08")).
                body("id",Matchers.is(5001));

    }

    @Test
    public void putTest(){

        RestAssured.baseURI="https://jsonplaceholder.typicode.com";

        given().
                pathParam("id", 500).
                body("{\n" +
                        "    \"albumId\": 12,\n" +
                        "    \"id\": 500,\n" +
                        "    \"title\": \"eum architector saepe qui nobis ea aut\",\n" +
                        "    \"url\": \"http://placehold.it/600/324309\",\n" +
                        "    \"thumbnailUrl\": \"http://placehold.it/150/324309\"\n" +
                        "}").
                contentType(ContentType.JSON).
                when().
                put("/photos/{id}").
                then().statusCode(200).and().contentType(ContentType.JSON).and().
                body("albumId",Matchers.is(12)).
                body("id",Matchers.is(500)).
                body("title",Matchers.equalTo("eum architector saepe qui nobis ea aut")).
                body("url",Matchers.equalTo("http://placehold.it/600/324309")).
                body("thumbnailUrl",Matchers.equalTo("http://placehold.it/150/324309"));


    }

    @Test
    public void patchTest(){

        RestAssured.baseURI="https://jsonplaceholder.typicode.com";

        given().
                pathParam("id", 630).
                body("{\n" +
                        "   \"title\": \"normalny tytul\"\n" +
                        "}").
                contentType(ContentType.JSON).
                when().
                patch("/photos/{id}").
                then().statusCode(200).and().contentType(ContentType.JSON).and().
                body("albumId",Matchers.is(13)).
                body("id",Matchers.is(630)).
                body("title",Matchers.equalTo("normalny tytul")).
                body("url",Matchers.equalTo("http://placehold.it/600/f7d7d")).
                body("thumbnailUrl",Matchers.equalTo("http://placehold.it/150/f7d7d"));

    }

    @Test
    public void deleteTest(){

        RestAssured.baseURI="https://jsonplaceholder.typicode.com";

        given().
                pathParam("id", 720).
                when().
                delete("/photos/{id}").
                then().statusCode(200);
    }

    @Test
    public void e2eTest(){

        RestAssured.baseURI="https://jsonplaceholder.typicode.com";

        int photoId =
        given().
                contentType(ContentType.JSON).
                body("{\n" +
                        "\"albumId\": 133,\n" +
                        "\"title\": \"normalny tytul\",\n" +
                        "\"url\": \"http://placehold.it/600/1b9d08\"\n" +
                        "}").
                when().
                post("/photos").
                then().statusCode(201).and().
                body("albumId",Matchers.is(133)).
                body("title",Matchers.equalTo("normalny tytul")).
                body("url",Matchers.equalTo("http://placehold.it/600/1b9d08")).
                body("id",Matchers.is(5001)).
                extract().path("id");


        given().
                pathParam("id", photoId).
                contentType(ContentType.JSON).
                body("{\n" +
                        "\"albumId\": 145\n"+
                        "}").
                when().
                patch("/photos/{id}").
                then().statusCode(200).and().
                body("id",Matchers.is(photoId)).
                body("albumId",Matchers.is(145));

        given().
                pathParam("id", photoId).
                contentType(ContentType.JSON).
                body("{\n" +
                        "    \"albumId\": 123,\n" +
                        "    \"id\": "+photoId+",\n" +
                        "    \"title\": \"some new normal title\",\n" +
                        "    \"url\": \"http://placehold.it/600/324309\",\n" +
                        "    \"thumbnailUrl\": \"http://placehold.it/150/324309\"\n" +
                        "}").
                when().
                put("/photos/{id}").
                then().statusCode(200).and().
                body("id",Matchers.is(photoId)).
                body("albumId",Matchers.is(123)).
                body("title",Matchers.equalTo("some new normal title")).
                body("url",Matchers.equalTo("http://placehold.it/600/324309")).
                body("thumbnailUrl",Matchers.equalTo("http://placehold.it/150/324309"));

        given().
                pathParam("id", photoId).
                when().
                get("/photos/{id}").
                then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
                body("id",Matchers.is(photoId)).
                body("albumId",Matchers.is(123)).
                body("title",Matchers.equalTo("some new normal title")).
                body("url",Matchers.equalTo("http://placehold.it/600/324309")).
                body("thumbnailUrl",Matchers.equalTo("http://placehold.it/150/324309"));

        given().
                pathParam("id", photoId).
                when().
                delete("/photos/{id}").
                then().statusCode(200);

    }
}



