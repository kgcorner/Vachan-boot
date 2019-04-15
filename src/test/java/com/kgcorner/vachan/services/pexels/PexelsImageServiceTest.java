package com.kgcorner.vachan.services.pexels;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.kgcorner.vachan.data.Image;
import com.kgcorner.vachan.util.MockServer;
import org.junit.*;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;


/*
Description : <Write is class Description>
Author: kumar
Created on : 15/4/19
*/
@RunWith(SpringRunner.class)
@EnableFeignClients
@SpringBootTest
public class PexelsImageServiceTest {

    @Autowired
    private PexelsImageService service;

    private static final int MAX_IMAGES_PER_PAGE = 20;

    private String url = "/v1/search";

    @BeforeClass
    public static void setupMockedPexelServer() {
        MockServer.createServer();
    }

    @AfterClass
    public static void shutDownServer() {
        MockServer.shutDown();
    }

    @Test
    public void getImages() {
        Map<String, String> query = new HashMap<>();
        query.put("page", "1");
        query.put("per_page", "20");
        MockServer.mockNewUrl(url, query, MOCKED_RESPONSE);
        List<Image> images = service.getImages(1);
        Assert.assertNotNull("Null returned for images", images);
        Assert.assertEquals("unexpected numbers of images are returned", 4, images.size());
        Assert.assertEquals("unexpected image url found",
            "https://images.pexels.com/photos/2083472/pexels-photo-2083472.jpeg?" +
                "auto=compress&cs=tinysrgb&h=650&w=940",
            images.get(0).getImageUrl()
            );
        Assert.assertEquals("Photographer is not matching","Louis", images.get(0).getPhotographer());
    }

    @Test
    public void getImagesWithQuery() {
        Map<String, String> query = new HashMap<>();
        query.put("page", "1");
        query.put("per_page", "20");
        query.put("query", "love");
        MockServer.mockNewUrl(url, query, MOCKED_RESPONSE_LOVE);
        List<Image> images = service.getImages("love", 1);
        Assert.assertNotNull("Null returned for images", images);
        Assert.assertEquals("unexpected numbers of images are returned", 4, images.size());
        Assert.assertEquals("unexpected values found",
            "https://images.pexels.com/photos/949586/pexels-photo-949586.jpeg?" +
                "auto=compress&cs=tinysrgb&h=650&w=940",
            images.get(0).getImageUrl()
        );
        Assert.assertEquals("Photographer is not matching","rovenimages.com",
            images.get(0).getPhotographer());
    }

    private static final String MOCKED_RESPONSE_LOVE = "{\n" +
        "    \"total_results\": 2471,\n" +
        "    \"page\": 1,\n" +
        "    \"per_page\": 4,\n" +
        "    \"photos\": [\n" +
        "        {\n" +
        "            \"id\": 949586,\n" +
        "            \"width\": 5472,\n" +
        "            \"height\": 3648,\n" +
        "            \"url\": \"https://www.pexels.com/photo/white-and-pink-floral-freestanding-letter-decor-949586/\",\n" +
        "            \"photographer\": \"rovenimages.com\",\n" +
        "            \"photographer_url\": \"https://www.pexels.com/@rovenimages-com-344613\",\n" +
        "            \"src\": {\n" +
        "                \"original\": \"https://images.pexels.com/photos/949586/pexels-photo-949586.jpeg\",\n" +
        "                \"large2x\": \"https://images.pexels.com/photos/949586/pexels-photo-949586.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940\",\n" +
        "                \"large\": \"https://images.pexels.com/photos/949586/pexels-photo-949586.jpeg?auto=compress&cs=tinysrgb&h=650&w=940\",\n" +
        "                \"medium\": \"https://images.pexels.com/photos/949586/pexels-photo-949586.jpeg?auto=compress&cs=tinysrgb&h=350\",\n" +
        "                \"small\": \"https://images.pexels.com/photos/949586/pexels-photo-949586.jpeg?auto=compress&cs=tinysrgb&h=130\",\n" +
        "                \"portrait\": \"https://images.pexels.com/photos/949586/pexels-photo-949586.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=1200&w=800\",\n" +
        "                \"landscape\": \"https://images.pexels.com/photos/949586/pexels-photo-949586.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=627&w=1200\",\n" +
        "                \"tiny\": \"https://images.pexels.com/photos/949586/pexels-photo-949586.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=200&w=280\"\n" +
        "            }\n" +
        "        },\n" +
        "        {\n" +
        "            \"id\": 556667,\n" +
        "            \"width\": 2430,\n" +
        "            \"height\": 3004,\n" +
        "            \"url\": \"https://www.pexels.com/photo/affection-afterglow-backlit-blur-556667/\",\n" +
        "            \"photographer\": \"luizclas\",\n" +
        "            \"photographer_url\": \"https://www.pexels.com/@luizclas-170497\",\n" +
        "            \"src\": {\n" +
        "                \"original\": \"https://images.pexels.com/photos/556667/pexels-photo-556667.jpeg\",\n" +
        "                \"large2x\": \"https://images.pexels.com/photos/556667/pexels-photo-556667.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940\",\n" +
        "                \"large\": \"https://images.pexels.com/photos/556667/pexels-photo-556667.jpeg?auto=compress&cs=tinysrgb&h=650&w=940\",\n" +
        "                \"medium\": \"https://images.pexels.com/photos/556667/pexels-photo-556667.jpeg?auto=compress&cs=tinysrgb&h=350\",\n" +
        "                \"small\": \"https://images.pexels.com/photos/556667/pexels-photo-556667.jpeg?auto=compress&cs=tinysrgb&h=130\",\n" +
        "                \"portrait\": \"https://images.pexels.com/photos/556667/pexels-photo-556667.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=1200&w=800\",\n" +
        "                \"landscape\": \"https://images.pexels.com/photos/556667/pexels-photo-556667.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=627&w=1200\",\n" +
        "                \"tiny\": \"https://images.pexels.com/photos/556667/pexels-photo-556667.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=200&w=280\"\n" +
        "            }\n" +
        "        },\n" +
        "        {\n" +
        "            \"id\": 1040626,\n" +
        "            \"width\": 6000,\n" +
        "            \"height\": 4000,\n" +
        "            \"url\": \"https://www.pexels.com/photo/heart-shaped-pink-and-purple-flower-garden-1040626/\",\n" +
        "            \"photographer\": \"shahbaz Akram\",\n" +
        "            \"photographer_url\": \"https://www.pexels.com/@shahbaz-akram-262965\",\n" +
        "            \"src\": {\n" +
        "                \"original\": \"https://images.pexels.com/photos/1040626/pexels-photo-1040626.jpeg\",\n" +
        "                \"large2x\": \"https://images.pexels.com/photos/1040626/pexels-photo-1040626.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940\",\n" +
        "                \"large\": \"https://images.pexels.com/photos/1040626/pexels-photo-1040626.jpeg?auto=compress&cs=tinysrgb&h=650&w=940\",\n" +
        "                \"medium\": \"https://images.pexels.com/photos/1040626/pexels-photo-1040626.jpeg?auto=compress&cs=tinysrgb&h=350\",\n" +
        "                \"small\": \"https://images.pexels.com/photos/1040626/pexels-photo-1040626.jpeg?auto=compress&cs=tinysrgb&h=130\",\n" +
        "                \"portrait\": \"https://images.pexels.com/photos/1040626/pexels-photo-1040626.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=1200&w=800\",\n" +
        "                \"landscape\": \"https://images.pexels.com/photos/1040626/pexels-photo-1040626.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=627&w=1200\",\n" +
        "                \"tiny\": \"https://images.pexels.com/photos/1040626/pexels-photo-1040626.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=200&w=280\"\n" +
        "            }\n" +
        "        },\n" +
        "        {\n" +
        "            \"id\": 264109,\n" +
        "            \"width\": 4643,\n" +
        "            \"height\": 3240,\n" +
        "            \"url\": \"https://www.pexels.com/photo/baby-children-cute-dress-264109/\",\n" +
        "            \"photographer\": \"Pixabay\",\n" +
        "            \"photographer_url\": \"https://www.pexels.com/@pixabay\",\n" +
        "            \"src\": {\n" +
        "                \"original\": \"https://images.pexels.com/photos/264109/pexels-photo-264109.jpeg\",\n" +
        "                \"large2x\": \"https://images.pexels.com/photos/264109/pexels-photo-264109.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940\",\n" +
        "                \"large\": \"https://images.pexels.com/photos/264109/pexels-photo-264109.jpeg?auto=compress&cs=tinysrgb&h=650&w=940\",\n" +
        "                \"medium\": \"https://images.pexels.com/photos/264109/pexels-photo-264109.jpeg?auto=compress&cs=tinysrgb&h=350\",\n" +
        "                \"small\": \"https://images.pexels.com/photos/264109/pexels-photo-264109.jpeg?auto=compress&cs=tinysrgb&h=130\",\n" +
        "                \"portrait\": \"https://images.pexels.com/photos/264109/pexels-photo-264109.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=1200&w=800\",\n" +
        "                \"landscape\": \"https://images.pexels.com/photos/264109/pexels-photo-264109.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=627&w=1200\",\n" +
        "                \"tiny\": \"https://images.pexels.com/photos/264109/pexels-photo-264109.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=200&w=280\"\n" +
        "            }\n" +
        "        }\n" +
        "    ],\n" +
        "    \"next_page\": \"https://api.pexels.com/v1/search/?page=2&per_page=4&query=love\"\n" +
        "}";

    private static final String MOCKED_RESPONSE = "{\n" +
        "    \"page\": 1,\n" +
        "    \"per_page\": 4,\n" +
        "    \"photos\": [\n" +
        "        {\n" +
        "            \"id\": 2083472,\n" +
        "            \"width\": 4000,\n" +
        "            \"height\": 5000,\n" +
        "            \"url\": \"https://www.pexels.com/photo/photo-of-person-holding-pineapple-fruit-in-front-of-face-2083472/\",\n" +
        "            \"photographer\": \"Louis\",\n" +
        "            \"photographer_url\": \"https://www.pexels.com/@louis-965146\",\n" +
        "            \"src\": {\n" +
        "                \"original\": \"https://images.pexels.com/photos/2083472/pexels-photo-2083472.jpeg\",\n" +
        "                \"large2x\": \"https://images.pexels.com/photos/2083472/pexels-photo-2083472.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940\",\n" +
        "                \"large\": \"https://images.pexels.com/photos/2083472/pexels-photo-2083472.jpeg?auto=compress&cs=tinysrgb&h=650&w=940\",\n" +
        "                \"medium\": \"https://images.pexels.com/photos/2083472/pexels-photo-2083472.jpeg?auto=compress&cs=tinysrgb&h=350\",\n" +
        "                \"small\": \"https://images.pexels.com/photos/2083472/pexels-photo-2083472.jpeg?auto=compress&cs=tinysrgb&h=130\",\n" +
        "                \"portrait\": \"https://images.pexels.com/photos/2083472/pexels-photo-2083472.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=1200&w=800\",\n" +
        "                \"landscape\": \"https://images.pexels.com/photos/2083472/pexels-photo-2083472.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=627&w=1200\",\n" +
        "                \"tiny\": \"https://images.pexels.com/photos/2083472/pexels-photo-2083472.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=200&w=280\"\n" +
        "            }\n" +
        "        },\n" +
        "        {\n" +
        "            \"id\": 1893602,\n" +
        "            \"width\": 2500,\n" +
        "            \"height\": 2000,\n" +
        "            \"url\": \"https://www.pexels.com/photo/three-smiling-woman-1893602/\",\n" +
        "            \"photographer\": \"rawpixel.com\",\n" +
        "            \"photographer_url\": \"https://www.pexels.com/@rawpixel\",\n" +
        "            \"src\": {\n" +
        "                \"original\": \"https://images.pexels.com/photos/1893602/pexels-photo-1893602.jpeg\",\n" +
        "                \"large2x\": \"https://images.pexels.com/photos/1893602/pexels-photo-1893602.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940\",\n" +
        "                \"large\": \"https://images.pexels.com/photos/1893602/pexels-photo-1893602.jpeg?auto=compress&cs=tinysrgb&h=650&w=940\",\n" +
        "                \"medium\": \"https://images.pexels.com/photos/1893602/pexels-photo-1893602.jpeg?auto=compress&cs=tinysrgb&h=350\",\n" +
        "                \"small\": \"https://images.pexels.com/photos/1893602/pexels-photo-1893602.jpeg?auto=compress&cs=tinysrgb&h=130\",\n" +
        "                \"portrait\": \"https://images.pexels.com/photos/1893602/pexels-photo-1893602.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=1200&w=800\",\n" +
        "                \"landscape\": \"https://images.pexels.com/photos/1893602/pexels-photo-1893602.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=627&w=1200\",\n" +
        "                \"tiny\": \"https://images.pexels.com/photos/1893602/pexels-photo-1893602.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=200&w=280\"\n" +
        "            }\n" +
        "        },\n" +
        "        {\n" +
        "            \"id\": 1028225,\n" +
        "            \"width\": 3456,\n" +
        "            \"height\": 5184,\n" +
        "            \"url\": \"https://www.pexels.com/photo/brown-wooden-house-on-edge-of-cliff-1028225/\",\n" +
        "            \"photographer\": \"Martin  Péchy\",\n" +
        "            \"photographer_url\": \"https://www.pexels.com/@martinpechy\",\n" +
        "            \"src\": {\n" +
        "                \"original\": \"https://images.pexels.com/photos/1028225/pexels-photo-1028225.jpeg\",\n" +
        "                \"large2x\": \"https://images.pexels.com/photos/1028225/pexels-photo-1028225.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940\",\n" +
        "                \"large\": \"https://images.pexels.com/photos/1028225/pexels-photo-1028225.jpeg?auto=compress&cs=tinysrgb&h=650&w=940\",\n" +
        "                \"medium\": \"https://images.pexels.com/photos/1028225/pexels-photo-1028225.jpeg?auto=compress&cs=tinysrgb&h=350\",\n" +
        "                \"small\": \"https://images.pexels.com/photos/1028225/pexels-photo-1028225.jpeg?auto=compress&cs=tinysrgb&h=130\",\n" +
        "                \"portrait\": \"https://images.pexels.com/photos/1028225/pexels-photo-1028225.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=1200&w=800\",\n" +
        "                \"landscape\": \"https://images.pexels.com/photos/1028225/pexels-photo-1028225.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=627&w=1200\",\n" +
        "                \"tiny\": \"https://images.pexels.com/photos/1028225/pexels-photo-1028225.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=200&w=280\"\n" +
        "            }\n" +
        "        },\n" +
        "        {\n" +
        "            \"id\": 1804796,\n" +
        "            \"width\": 3276,\n" +
        "            \"height\": 4096,\n" +
        "            \"url\": \"https://www.pexels.com/photo/silhouette-of-girl-during-evening-1804796/\",\n" +
        "            \"photographer\": \"luizclas\",\n" +
        "            \"photographer_url\": \"https://www.pexels.com/@luizclas-170497\",\n" +
        "            \"src\": {\n" +
        "                \"original\": \"https://images.pexels.com/photos/1804796/pexels-photo-1804796.jpeg\",\n" +
        "                \"large2x\": \"https://images.pexels.com/photos/1804796/pexels-photo-1804796.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940\",\n" +
        "                \"large\": \"https://images.pexels.com/photos/1804796/pexels-photo-1804796.jpeg?auto=compress&cs=tinysrgb&h=650&w=940\",\n" +
        "                \"medium\": \"https://images.pexels.com/photos/1804796/pexels-photo-1804796.jpeg?auto=compress&cs=tinysrgb&h=350\",\n" +
        "                \"small\": \"https://images.pexels.com/photos/1804796/pexels-photo-1804796.jpeg?auto=compress&cs=tinysrgb&h=130\",\n" +
        "                \"portrait\": \"https://images.pexels.com/photos/1804796/pexels-photo-1804796.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=1200&w=800\",\n" +
        "                \"landscape\": \"https://images.pexels.com/photos/1804796/pexels-photo-1804796.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=627&w=1200\",\n" +
        "                \"tiny\": \"https://images.pexels.com/photos/1804796/pexels-photo-1804796.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=200&w=280\"\n" +
        "            }\n" +
        "        }\n" +
        "    ],\n" +
        "    \"next_page\": \"https://api.pexels.com/v1/curated/?page=2&per_page=4\"\n" +
        "}";
}