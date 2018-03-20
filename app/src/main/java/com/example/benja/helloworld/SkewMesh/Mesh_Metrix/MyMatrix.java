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
}
