import org.bytedeco.javacpp.opencv_core;

import java.util.List;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_highgui.cvShowImage;
import static org.bytedeco.javacpp.opencv_highgui.cvWaitKey;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class Fun {

    public static void main(String[] args){

        List<List<Point>> formes = OpenCVTestKt.loadAndAnalyseImage("square.png");
        for(List<Point> forme : formes) {
            for(Point coin : forme)
                System.out.println(OpenCVTestKt.pointToString(coin));
        }

        String file = "petri_petit.png";
        IplImage image = cvLoadImage(file);
        IplImage image2 = cvLoadImage(file);
        IplImage image3 = cvLoadImage(file);
        IplImage image4 = cvLoadImage(file);
        IplImage image5 = cvLoadImage(file);
        IplImage image6 = cvLoadImage(file);
        IplImage imageTest = cvLoadImage(file);


        //Toutes les différentes matrice par lesquelles on doit passer
        Mat matrice = new Mat(image);
        Mat matrice2= new Mat(image2);
        Mat matrice3= new Mat(image3);
        Mat matrice4= new Mat(image4);
        Mat matrice5= new Mat(image5);
        Mat matrice6= new Mat(image6);
        Mat matriceTest = new Mat(imageTest);

        //Matrices pour le masque 1 FILTRE COLOR
        double[] mincolor = {0,100,80};
        Mat mask1 = new Mat(mincolor);
        double[] maxcolor = {10,256,256};
        Mat mask2 = new Mat(maxcolor);

        //Matrices pour le masque 2 FILTRE BRIGHTNESS
        double[] mincolor2 = {0,0,0};
        Mat mask12 = new Mat(mincolor2);
        double[] maxcolor2 = {255,255,255};
        Mat mask22 = new Mat(maxcolor2);

        cvtColor(matrice,matrice2,CV_BGR2RGB);
        GaussianBlur(matrice2,matrice3,new Size(7,7),0);
        cvtColor(matrice3,matrice4,CV_RGB2HSV);

        blur(matriceTest,matriceTest, new Size(3,3));
        cvtColor(matriceTest,matriceTest,CV_BGR2GRAY);
        Canny(matriceTest,matriceTest,100,600);

        System.out.println(mask1.toString());
        System.out.println(mask2.toString());
        System.out.println(matrice4.toString());
        System.out.println("AFTER");
        System.out.println(mask12.toString());
        System.out.println(mask22.toString());
        System.out.println(matrice5.toString());

        //Filtrage couleur
        inRange(matrice4,mask1,mask2,matrice5);
        //Filtrage luminosité
        //inRange(matrice5,mask12,mask22,matrice6);
        //AFFICHAGE *-*-*-*-*-**-*

        IplImage matriceImage = new IplImage(matrice);
        IplImage matriceImag2 = new IplImage(matrice2);
        IplImage matriceImag3 = new IplImage(matrice3);
        IplImage matriceImag4 = new IplImage(matrice4);
        IplImage matriceImag5 = new IplImage(matrice5);
        IplImage matriceImag6 = new IplImage(matrice6);
        IplImage matriceImagTest = new IplImage(matriceTest);

        //cvShowImage("0.jpg",image);
    /*  cvShowImage("1.jpg",matriceImage);
        cvShowImage("2.jpg",matriceImag2);
        cvShowImage("3.jpg",matriceImag3);
        cvShowImage("4.jpg",matriceImag4);
        cvShowImage("5.jpg",matriceImag5);
        cvShowImage("6.jpg",matriceImag6);
     */
        cvShowImage("Test.jpg",matriceImagTest);
        cvWaitKey();

    /*    System.out.println("Here1");
        cvReleaseImage(image);
        cvReleaseImage(image2);
        cvReleaseImage(image3);
        cvReleaseImage(image4);
        cvReleaseImage(image5);
        System.out.println("Here2");
        cvReleaseImage(matriceImage);
        cvReleaseImage(matriceImag2);
        cvReleaseImage(matriceImag3);
        cvReleaseImage(matriceImag4);
        cvReleaseImage(matriceImag5);
        System.out.println("Here3");
    */



    }




}
