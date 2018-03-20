package com.example.benja.helloworld.SkewMesh.Mesh_Metrix;

/**
 * Created by benja on 2018/3/20.
 */

public class MyMatrix {
    public int row,column;

    public int[][] matrix;

    public MyMatrix(int row, int column) {
        this.row = row;
        this.column = column;
        initMatrix();
    }

    private void initMatrix(){
        matrix=new int[row][column];
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

    public int[][] preMultiple(MyMatrix myMatrix){   //column=myMatrix.row
        int[][] matrix=new int[row][myMatrix.column];

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

    public int[][] postMultiple(MyMatrix myMatrix){
        int[][] matrix=new int[myMatrix.row][this.column];

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
