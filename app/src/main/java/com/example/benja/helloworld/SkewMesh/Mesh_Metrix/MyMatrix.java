package com.example.benja.helloworld.SkewMesh.Mesh_Metrix;

/**
 * Created by benja on 2018/3/20.
 */

public class MyMatrix {
    public int row,column;

    public float[][] matrix;

    public MyMatrix(int row, int column) {
        this.row = row;
        this.column = column;
        initMatrix();
    }

    public void initMatrix(){
        matrix=null;
        matrix=new float[row][column];
        for(int i=0;i<row;i++) {
            for (int j=0;j<column;j++){
                if (i==j){
                    matrix[i][j]=1;
                }else {
                    matrix[i][j]=0;
                }
            }
        }
    }

    public float[][] preMultiple(MyMatrix myMatrix){   //column=myMatrix.row
        float[][] matrix=new float[row][myMatrix.column];

        for(int i=0;i<row;i++){
           for(int j=0;j<myMatrix.column;j++){
               matrix[i][j]=0;
               for(int k=0;k<column;k++){
                   matrix[i][j]+=this.matrix[i][k]*myMatrix.matrix[k][j];
               }
           }
        }
        return matrix;
    }

    public float[][] postMultiple(MyMatrix myMatrix){
        float[][] matrix=new float[myMatrix.row][this.column];

        for(int i=0;i<myMatrix.row;i++){
            for(int j=0;j<column;j++){
                matrix[i][j]=0;
                for(int k=0;k<column;k++){
                    matrix[i][j]+=myMatrix.matrix[i][k]*this.matrix[k][j];
                }
            }
        }

        return matrix;
    }

    public void setMatrix(MyMatrix myMatrix){
        this.matrix=myMatrix.matrix.clone();
    }
}
