/*import org.bytedeco.javacpp.opencv_core;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_highgui.cvShowImage;
import static org.bytedeco.javacpp.opencv_highgui.cvWaitKey;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvSaveImage;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.opencv.imgproc.Imgproc.CV_GAUSSIAN;
import org.opencv.core.Core;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Application {

    public static void main(String[] args){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        opencv_core.IplImage image = cvLoadImage("color.jpg");
        opencv_core.IplImage transfoImage = cvCreateImage(cvGetSize(image),8,3);
        opencv_core.IplImage blurImage =  cvCreateImage(cvGetSize(image),8,3);
        opencv_core.IplImage blurImageHSV = cvCreateImage(cvGetSize(image),8,3);
        //Mat matrice = new Mat(image);

        cvCvtColor(image,transfoImage,CV_BGR2RGB);
        cvSmooth(transfoImage,blurImage,CV_GAUSSIAN,7,7,0,0);
        cvCvtColor(blurImage,blurImageHSV,CV_RGB2HSV);

        CvScalar minc = cvScalar(160,150,75,0);
        CvScalar maxc = cvScalar(180,255,255,0);

        opencv_core.IplImage mask1 = cvCreateImage(cvGetSize(blurImageHSV),8,1);

        cvInRangeS(blurImageHSV,minc,maxc,mask1);

        CvScalar minc2 = cvScalar(170,100,80,0);
        CvScalar maxc2 = cvScalar(180,255,255,0);

        opencv_core.IplImage mask2 = cvCreateImage(cvGetSize(blurImageHSV),8,1);

        cvInRangeS(blurImageHSV,minc2,maxc2,mask2);

        opencv_core.IplImage mask3 = cvCreateImage(cvGetSize(blurImageHSV),8,1);

        cvCopy(mask1,mask3,mask1);
        Mat mat = getStructuringElement(CV_SHAPE_ELLIPSE,new Size(15,15));
        cvMorphologyEx(mask2,mask3,mask1);
        cvMorphologyEx();
        //cvShowImage("transfoImage.jpg",transfoImage);
        //cvShowImage("blurImage.jpg",blurImage);
       // cvShowImage("blurImageHSV.jpg",blurImageHSV);
        cvShowImage("mask1.jpg",mask1);
        cvShowImage("mask2.jpg",mask2);
        cvShowImage("mask3.jpg",mask3);
        cvWaitKey();


        cvReleaseImage(image);
        cvReleaseImage(transfoImage);







    }




}
*/