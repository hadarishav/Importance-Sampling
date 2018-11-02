
import org.apache.commons.math3.distribution.NormalDistribution;
import java.io.*;
import java.lang.Object;
import org.jfree.data.statistics.HistogramDataset;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.math3.distribution.UniformRealDistribution;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramType;

public class mas4 {

    public static void main(String[] args) {
       
        int size = 1000;
        mas4 m=new mas4();
        double mu=0;
        int lb=-5,ub=5;
        double sigma=1;     
        double expval=0;
	NormalDistribution f1 = new NormalDistribution();
        UniformRealDistribution g1 = new UniformRealDistribution(lb,ub);
        expval=m.IS_1(size,mu,sigma,lb,ub,f1,g1);
        System.out.println("Mean Expected value for plot 6.7.1: "+expval);
        UniformRealDistribution g2 = new UniformRealDistribution(-1,1);
        expval=m.IS_2(1000,-1,1,g2);
        System.out.println("Mean Expected value for plot 6.7.2: "+expval);
    }
    double IS_1(int size,double mu,double sigma,int lb,int ub, NormalDistribution f1,UniformRealDistribution g1)
    {
        Random rnd=new Random();
        double xi[]=new double[size];
        double yi[]=new double[size];
        double wt=0,num,denum;
        double d = 0,f=0,mean=0,max=0.0,min=10000;
        double expval[] =new double[1000];
        for (int i=0;i<1000;i++)
        {
            for(int j=0;j<size;j++)
            {
                f=lb+(ub-lb)*rnd.nextDouble();
                num=f1.density(f);    //p(x)
                denum=g1.density(f);    //q(x)
                wt=num/denum;         //importance weight
                expval[i]=expval[i]+(f*f)*wt;
            }
            expval[i]=expval[i]/size;
            if(expval[i]>max)
            max=expval[i];
        if(expval[i]<min)
            min=expval[i];
        }
        for(int i=0;i<1000;i++)
              mean+=expval[i];
        mean=mean/1000;
        System.out.println("Maximum expected value for plot 6.7.1:"+max);
        System.out.println("Minimum expected value for plot 6.7.1:"+min);
        HistogramDataset dataset = new HistogramDataset();
        dataset.setType(HistogramType.FREQUENCY);
        dataset.addSeries("Importance Sampling",expval,50);
        JFreeChart chart = ChartFactory.createHistogram("Plot 6.7.1", "Expected Value", "Frequency", dataset,
        PlotOrientation.VERTICAL, true, true, false);
        ChartFrame frame = new ChartFrame("Importance Sampling", chart);
        frame.pack();
        frame.setVisible(true);
        return(mean);
    }
    double IS_2(int size,int lb,int ub, UniformRealDistribution g1)
    {
        Random rnd=new Random();
        double num=0.0;
        double denum=0.0;
        double wt=0.0,mean=0.0;
        double f=0.0,max=0.0,min=10000;
        double expval[]=new double[1000];
        for (int i=0;i<1000;i++)
        {
            for(int j=0;j<size;j++)
            {
                f=lb+(ub-lb)*rnd.nextDouble();
                num=((1.0+Math.cos(Math.PI*f))/2.0); //p(x)          
                denum=g1.density(f);    //q(x)
                wt=num/denum; //importance weight
                expval[i]=expval[i]+((f*f)*wt);
            }
        expval[i]=expval[i]/size;
        if(expval[i]>max)
            max=expval[i];
        if(expval[i]<min)
            min=expval[i];
        }
        for(int i=0;i<1000;i++)
              mean+=expval[i];
        mean=mean/1000;
        System.out.println("Maximum expected value for plot 6.7.2:"+max);
        System.out.println("Minimum expected value for plot 6.7.2:"+min);
        HistogramDataset dataset = new HistogramDataset();
        dataset.setType(HistogramType.FREQUENCY);
        dataset.addSeries("Importance Sampling",expval,50);
        JFreeChart chart = ChartFactory.createHistogram("Plot 6.7.2", "Expected Value", "Frequency", dataset,
        PlotOrientation.VERTICAL, true, true, false);
        ChartFrame frame = new ChartFrame("Importance Sampling", chart);
        frame.pack();
        frame.setVisible(true);
        return(mean);
    }   
}
