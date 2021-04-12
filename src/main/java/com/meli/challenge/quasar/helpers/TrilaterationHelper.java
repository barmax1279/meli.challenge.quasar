package com.meli.challenge.quasar.helpers;

import com.meli.challenge.quasar.entities.Distance;
import com.meli.challenge.quasar.entities.Position;
import org.la4j.Matrix;
import org.la4j.inversion.GaussJordanInverter;
import org.la4j.matrix.DenseMatrix;
import org.la4j.vector.DenseVector;

import java.util.List;

public class TrilaterationHelper {
    /**
     * En esta clase es para determinar la posicion de un punto a partir de minimamente 3 cordenadas
     * y desde el punto a determinar se necesitan conocer las distancias a cada uno de los puntos.
     * Para resolver el problema y calcular la ubicacion se usa un metodo matematico trilateracion de 2D.
     * Para mas información
     *
     * Referencias
     * https://es.wikipedia.org/wiki/Trilateraci%C3%B3n
     * https://www.youtube.com/watch?v=4fXjc9uibGM
     * https://www.youtube.com/watch?v=ktPuxq3UVX4
     *
     *
     * Una vez obtina la matriz de ecuaciones simultaneas con dos incognita,
     * se utilizo algebra lineal para resolver esas incognitas
     * (x - a)^2 + (y - b)^2 = r1^2
     * (x - a)^2 + (y - b)^2 = r2^2
     * (x - a)^2 + (y - b)^2 = r3^2

     *
     * @param distances        Una lista con la ubicacion de los satelites,
     *                         la distancia relativa al punto a ubicar

     * @return Contine la ubicacion X e Y
     */

    public  static Position calcular(List<Distance> distances)
    {
        Distance dist1 = distances.get(0);
        Distance dist2 = distances.get(1);
        Distance dist3 = distances.get(2);
        double[] pointA = {dist1.getPosition().getX(), dist1.getPosition().getY()};
        double[] pointB = {dist2.getPosition().getX(), dist2.getPosition().getY()};
        double[] pointC = {dist3.getPosition().getX(), dist3.getPosition().getY()};
        double distanceA = dist1.getDistance();
        double distanceB = dist2.getDistance();
        double distanceC = dist3.getDistance();


        DenseVector vA = DenseVector.fromArray(pointA);

        //Declare elements of b vector
        //bBA = 1/2 * (rA^2 - rB^2 + dBA^2)
        double[] b = { 0, 0 };
        b[0] = 0.5 * (Math.pow(distanceA, 2) - Math.pow(distanceB, 2) + Math.pow(getDistance(pointB, pointA), 2));
        b[1] = 0.5 * (Math.pow(distanceA, 2) - Math.pow(distanceC, 2) + Math.pow(getDistance(pointC, pointA), 2));
        //Convert b array to vector form

        //Build A array
        //A =   {x2 -x1, y2 - y1}
        //      {x3 - x1, y3 - y1}
        double[][] A = {
                { pointB[0] - pointA[0], pointB[1] - pointA[1] },
                { pointC[0] - pointA[0], pointC[1] - pointA[1] }
        };
        //Convert A to Matrix form
        // Matrix mA = Matrix<double>.Build.DenseOfArray(A);
        DenseMatrix mA = DenseMatrix.from2DArray(A);
        //Declare Transpose of A matrix;
        DenseMatrix mAT = mA.transpose().toDenseMatrix();

        //Declare solution vector x to 0
        DenseVector x;
        DenseVector vb = DenseVector.fromArray(b);

        Matrix mMulp = mAT.multiply(mA);

        Matrix mInv = new GaussJordanInverter(mMulp).inverse();
        x = mInv.multiply( mAT.multiply( vb) ).toDenseVector();

        double[] point = x.add(vA).toDenseVector().toArray();
        return new Position( Math.round( point[0]*100)/100.00 , Math.round(point[1]*100)/100.00 );
    }

    private static double getDistance(double[] p1, double[] p2) {
        //d^2 = (p1[0] - p2[0])^2 + (p1[1] - p2[1]);

        //Calculo del modulo de un vector
        double distSquared = Math.pow((p1[0] - p2[0]), 2) + Math.pow((p1[1] - p2[1]), 2);
        return Math.sqrt(distSquared);
    }
}
