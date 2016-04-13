package com.tigerit.exam;
import java.util.ArrayList;

import static com.tigerit.exam.IO.*;

/**
 * All of your application logic should be placed inside this class.
 * Remember we will load your applicasstion from our custom container.
 * You may add private method inside this class but, make sure your
 * application's execution points start from inside run method.
 */
public class Solution implements Runnable {

    public static ArrayList< ArrayList<String> > tables_name = new ArrayList< ArrayList<String> >();
    public static ArrayList< ArrayList< ArrayList<Integer> > > tables = new ArrayList<ArrayList<ArrayList<Integer>>>();
    @Override
    public void run()
    {
        int i,j,k,l;
        int T, nT, nC, nD, nQ;

        IO reader = new IO();
        T = reader.readLineAsInteger();
        //System.out.println(T);
        for(l=0;l<T;l++)
        {
            System.out.println("Test: "+(l+1));
            nT = reader.readLineAsInteger();
            for(i=0;i<nT;i++)
            {
                ArrayList<String> tem = new ArrayList<>();
                String name = reader.readLine();
                tem.add(name);
                String nCnD = reader.readLine();
                String[] nCnDS = nCnD.split(" +");
                nC = Integer.parseInt(nCnDS[0]);
                nD = Integer.parseInt(nCnDS[1]);
                //System.out.println(nC+" "+nD+" ");
                String col = reader.readLine();
                String[] colmn = col.split(" +");
                for(int c=0;c<nC;c++)tem.add(colmn[c]);
                tables_name.add(tem);
                ArrayList< ArrayList<Integer> > temp2 = new ArrayList< ArrayList<Integer> >();
                for(j=0;j<nD;j++)
                {
                    ArrayList<Integer> temp = new ArrayList<Integer>();
                    String dat = reader.readLine();
                    String[] data = dat.split(" +");
                    for(k=0;k<nC;k++)
                    {
                        int s = Integer.parseInt(data[k]);
                        temp.add(s);
                    }
                    temp2.add(temp);
                }
                tables.add(temp2);
            }

            // SQL input & Processing...
            nQ = reader.readLineAsInteger();
            for(k=0;k<nQ;k++)
            {
                ArrayList<String> required_tokens = new ArrayList<String>();
                String line1 = reader.readLine();
                //System.out.println(line1);
                String[] words = line1.split(" +");
                if(words[1].equals("*"))
                {
                    required_tokens.add("*");
                    //System.out.println("*");
                }
                else
                {
                    for(i=0;i<words.length;i++)
                    {
                        String[] token = words[i].split("[.,]");
                        for(int r=0;r<token.length;r++)
                        {
                            if(r==1) // table1.a1, here a1 is in index 1
                            {
                                required_tokens.add(token[r]);
                                //System.out.println(token[r]);
                            }
                        }
                    }
                }

                String line2 = reader.readLine();
                //System.out.println(line1);
                words = line2.split(" +");
                required_tokens.add(words[1]);
                //System.out.println(words[1]);

                String line3 = reader.readLine();
                //System.out.println(line1);
                words = line3.split(" +");
                required_tokens.add(words[1]);
                //System.out.println(words[1]);

                String line4 = reader.readLine();
                //System.out.println(line1);
                words = line4.split(" +");
                for(i=0;i<words.length;i++)
                {
                    String[] token = words[i].split("\\.");// or [.]
                    for(int r=0;r<token.length;r++)
                    {
                        if(r==1)
                        {
                            required_tokens.add(token[r]);
                            // System.out.println(token[r]);
                        }
                    }
                    //System.out.println(words[i]);
                }
                // end of getting 4 line SQL...
                System.out.println();
                join(required_tokens);
                System.out.println();
            }
        }
    }

    public static void join( ArrayList<String> tokens )
    {
        //System.out.println("From Method: ");
        int len = tokens.size();
        int t1 = len-4;             // table1 index in tokens
        int t2 = len-3;
        int atb1 = len-2;          // attribute1 in table1 which is used check condition
        int atb2 = len-1;
        int table1_indx=-1;
        int table2_indx=-1;
        int attribute1 = -1;
        int attribute2 = -1;

        //finding tables in global table names
        for(int i=0;i<tables_name.size();i++)
        {
            if(tokens.get(t1).equals(tables_name.get(i).get(0)))table1_indx = i;
        }
        for(int i=0;i<tables_name.size();i++)
        {
            if(tokens.get(t2).equals(tables_name.get(i).get(0)))table2_indx = i;
        }
        //System.out.println("table1: "+table1_indx+" table2: "+table2_indx);

        //finding comparison attributes in table
        for(int i=0;i<tables_name.get(table1_indx).size();i++)
        {
            //System.out.println(tables.get(table1_indx).get(0).get(i));
            if(tokens.get(atb1).equals(tables_name.get(table1_indx).get(i)))attribute1 = i-1;// i-1 cuz index 0 hold table name, thn others are contains the col name
        }
        for(int i=0;i<tables_name.get(table2_indx).size();i++)
        {
            if(tokens.get(atb2).equals(tables_name.get(table2_indx).get(i)))attribute2 = i-1;
        }
        //System.out.println("attribute1: "+attribute1+" attribute2: "+attribute2);

        if( is_result(table1_indx,table2_indx,attribute1,attribute2))
        {
            //System.out.println("true");
            if(tokens.get(0).equals("*"))
            {
                //System.out.println("select all....");
                // printing col name.........................................
                for(int i=1;i<tables_name.get(table1_indx).size();i++)
                {
                    if(i==1)System.out.print(tables_name.get(table1_indx).get(i));
                    else System.out.print(" "+tables_name.get(table1_indx).get(i));
                }
                for(int i=1;i<tables_name.get(table2_indx).size();i++)
                {
                    System.out.print(" "+tables_name.get(table2_indx).get(i));
                }
                System.out.println();
                // printing values.......................................
                for(int i=0;i<tables.get(table1_indx).size();i++)
                {
                    //System.out.println("loop"+i);
                    //System.out.println(tables.get(table1_indx).get(i).get(attribute1));
                    for(int j=0;j<tables.get(table2_indx).size();j++)
                    {
                        //System.out.println("loop"+j);
                        //System.out.println(tables.get(table2_indx).get(j).get(attribute2));
                        if(tables.get(table1_indx).get(i).get(attribute1) == tables.get(table2_indx).get(j).get(attribute2))
                        {
                            //System.out.println(" "+i+" "+j);
                            for(int r=0;r<tables.get(table1_indx).get(i).size();r++)
                            {
                                System.out.print(tables.get(table1_indx).get(i).get(r)+" ");
                                //System.out.printf("");
                            }
                            for(int r=0;r<tables.get(table2_indx).get(j).size();r++)
                            {
                                if(r==0)System.out.print(tables.get(table2_indx).get(j).get(r));
                                else System.out.printf(" "+tables.get(table2_indx).get(j).get(r));
                            }
                            System.out.println();
                        }
                    }
                }
            }
            else // not select *, some attribute only................
            {
                int print_done_in_table1=0;
                ArrayList<String> selected_col = new ArrayList<String>();
                for(int i=0;i<len-4;i++)selected_col.add(tokens.get(i));
                // printing col names
                for(int i=1;i<tables_name.get(table1_indx).size();i++)
                {
                    for(int j=0;j<selected_col.size();j++)
                    {
                        if(tables_name.get(table1_indx).get(i).equals(selected_col.get(j)))
                        {
                            //print_done_in_table1 = 1;
                            System.out.print(tables_name.get(table1_indx).get(i)+" ");
                        }
                    }
                }
                for(int i=1;i<tables_name.get(table2_indx).size();i++)
                {
                    for(int j=0;j<selected_col.size();j++)
                    {
                        if(tables_name.get(table2_indx).get(i).equals(selected_col.get(j)))
                        {
                            System.out.print(tables_name.get(table2_indx).get(i)+" ");
                        }
                    }
                }
                System.out.println();
                // printing join values
                for(int i=0;i<tables.get(table1_indx).size();i++)
                {
                    for(int j=0;j<tables.get(table2_indx).size();j++)
                    {
                        if(tables.get(table1_indx).get(i).get(attribute1)==tables.get(table2_indx).get(j).get(attribute2))
                        {
                            // printing from 1st table
                            for(int r=0;r<tables.get(table1_indx).get(i).size();r++)
                            {
                                for(int z=0;z<selected_col.size();z++)
                                {
                                    if(tables_name.get(table1_indx).get(r+1).equals(selected_col.get(z)))
                                    {
                                        System.out.printf(tables.get(table1_indx).get(i).get(r)+" ");
                                    }
                                }
                            }
                            // printing from 2nd table
                            for(int r=0;r<tables.get(table2_indx).get(j).size();r++)
                            {
                                for(int z=0;z<selected_col.size();z++)
                                {
                                    if(tables_name.get(table2_indx).get(r+1).equals(selected_col.get(z)))
                                    {
                                        System.out.printf(tables.get(table2_indx).get(j).get(r)+" ");
                                    }
                                }
                            }
                            System.out.println();
                        }
                    }
                }
            }
        }
    }

    public static boolean is_result(int t1, int t2, int at1, int at2 )
    {
        boolean status = false;
        for(int i=0;i<tables.get(t1).size();i++)
        {
            for(int j=0;j<tables.get(t2).size();j++)
            {
                if(tables.get(t1).get(i).get(at1).equals(tables.get(t2).get(j).get(at2)))
                {
                    status = true;
                }
            }
        }
        return status;
    }
}
