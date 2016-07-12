

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.example.project.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;                                                          
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class BarChartScaleBuilder extends Activity {
  private XYMultipleSeriesDataset mDataset;
  private XYMultipleSeriesRenderer mRenderer;
  private XYSeries mCurrentSeries;
  private XYSeriesRenderer mCurrentRenderer;
  private GraphicalView mChartView;
  private static final int BAR_CHART_SCALE_PREF_RESULT=1;
  double curValue;
  int curIndex;
  double xMax=-1;
  double yMax=-1;
  float correctionVal=0;
  ArrayList<Float> X;
  ArrayList<Float> Y;
  
  @Override
  protected void onSaveInstanceState(Bundle outState) 
  {
    super.onSaveInstanceState(outState);
    //�ٽ� ������ ���� ����ؼ� ���� ������ ����.
    outState.putSerializable("dataset", mDataset);
    outState.putSerializable("renderer", mRenderer);
    outState.putSerializable("current_series", mCurrentSeries);
    outState.putSerializable("current_renderer", mCurrentRenderer);
  }
  @Override
  protected void onRestoreInstanceState(Bundle savedState) 
  {
    super.onRestoreInstanceState(savedState);
    //�ٽ� �������� ��, �����ߴ� ������ ����
    mDataset = (XYMultipleSeriesDataset) savedState.getSerializable("dataset");
    mRenderer = (XYMultipleSeriesRenderer) savedState.getSerializable("renderer");
    mCurrentSeries = (XYSeries) savedState.getSerializable("current_series");
    mCurrentRenderer = (XYSeriesRenderer) savedState.getSerializable("current_renderer");
  }
  @Override
  protected void onCreate(Bundle savedInstanceState) 
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.show_graph);
    
    mDataset = new XYMultipleSeriesDataset();
    mRenderer = new XYMultipleSeriesRenderer();
    
    mRenderer.setPanEnabled(true, false);
    mRenderer.setApplyBackgroundColor(true);
    mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));
    
    DisplayMetrics metrics =getApplicationContext().getResources().getDisplayMetrics();
    correctionVal = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 13, metrics);	//ũ���������� ������������
    
    mRenderer.setAxisTitleTextSize(correctionVal);
    mRenderer.setChartTitleTextSize(correctionVal);
    mRenderer.setLabelsTextSize(correctionVal);
    mRenderer.setLegendTextSize(correctionVal);
    mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
    mRenderer.setZoomButtonsVisible(true);
    mRenderer.setBarWidth(correctionVal/2);		//bar�ϳ��� width ���� ����
   
    Random dice=new Random();
    int color=Color.argb(dice.nextInt(128)+128,dice.nextInt(64)+192,dice.nextInt(64)+192,dice.nextInt(64)+192);
    
    X=(ArrayList<Float>)getIntent().getSerializableExtra("x");
    Y=(ArrayList<Float>)getIntent().getSerializableExtra("y");
    
    XYSeries series = new XYSeries("Data name");
    mDataset.addSeries(series);
    mCurrentSeries = series;
    XYSeriesRenderer renderer = new XYSeriesRenderer();
    mRenderer.addSeriesRenderer(renderer);
    renderer.setChartValuesSpacing((correctionVal/1.5f));
    renderer.setColor(color);//������ -- scale�� ������ ���� ����, category�� ���� �ٸ� ����.
    renderer.setDisplayChartValues(true);
    renderer.setDisplayChartValuesDistance(10);
    renderer.setDisplayBoundingPoints(true);
    renderer.setChartValuesTextSize(correctionVal);
    mRenderer.setXTitle("X chook");
    mRenderer.setYTitle("Y chook");
    mCurrentRenderer = renderer;
    
    for (int i=0;i<X.size();i++)
    {
    	System.out.println(X.get(i)+", "+Y.get(i));
 
    	mCurrentSeries.add(X.get(i),Y.get(i));
    	if (xMax<X.get(i))
    		xMax=X.get(i);
    	if (yMax<Y.get(i))
    		yMax=Y.get(i);
    }
    System.out.println("initialize complete!");
    setScreenScale();
    /* capture �κ�
    Button captureBtn=(Button)findViewById(R.id.barcapture);
    captureBtn.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			try
			{
				Bitmap bit=mChartView.toBitmap();
				String path=getFilesDir().getAbsolutePath()+"/output.png";
				System.out.println(path);
				FileOutputStream fout=new FileOutputStream(path);
				bit.compress(Bitmap.CompressFormat.PNG,100,fout);
				fout.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	});*/
    
    Button setting=(Button)findViewById(R.id.showSettingBT);
    setting.setOnClickListener(new View.OnClickListener() 
    {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
	});
  }
  
  protected void setScreenScale()
  {
	  mRenderer.setXAxisMin(X.get(0)-correctionVal/4);
	  mRenderer.setYAxisMin(0.0);
	  mRenderer.setXAxisMax(xMax+correctionVal/2);		//�ʱ� ȭ���� x�� range�� 0~�������� X���ִ�+������/2, y�൵ ��������
	  mRenderer.setYAxisMax(yMax+correctionVal/4);
  }

  @Override
  protected void onResume() 
  {
    super.onResume();
    if (mChartView == null) 
    {
      LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
      mChartView = ChartFactory.getBarChartView(this, mDataset, mRenderer,BarChart.Type.DEFAULT);
      mRenderer.setClickEnabled(true);
      mRenderer.setSelectableBuffer(10);
      mChartView.setOnClickListener(new View.OnClickListener() 
      {
        public void onClick(View v) 
        {
          // handle the click event on the chart
          SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
          if (seriesSelection != null)
          {
            Toast.makeText(
            BarChartScaleBuilder.this,
            "X Value : " + seriesSelection.getXValue() + ", Y Value : "+ seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
          }
        }
      });
      /*
      mChartView.setOnLongClickListener(new View.OnLongClickListener() {
		@Override
		public boolean onLongClick(View v) {
			// TODO Auto-generated method stub
			try
			{
		        SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
		        curValue=seriesSelection.getValue();
		        curIndex=seriesSelection.getPointIndex();
				Intent myIntent=new Intent(BarChartScaleBuilder.this,BarChartPreference.class);
				startActivityForResult(myIntent,BAR_CHART_SCALE_PREF_RESULT);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return false;
		}
	});*/	//longClick ���մϴ�.
      layout.addView(mChartView, new LayoutParams(LayoutParams.MATCH_PARENT,
          LayoutParams.MATCH_PARENT));
    } 
    else 
    {
      mChartView.repaint();
    }
  }
  
  protected void onActivityResult(int requestCode,int resultCode,Intent data)
  {
	  super.onActivityResult(requestCode, resultCode,data);
	  if (requestCode==BAR_CHART_SCALE_PREF_RESULT)
	  {
		  changeSetting();
	  }
  }

  public void changeSetting()
  {
	  XYSeries tmp=mDataset.getSeriesAt(0);		//������ ���� series������ �ٽ� �������.
	  double X=tmp.getX(curIndex);

	  System.out.println("pressed idx :"+curIndex+", X :"+X);
	  SharedPreferences sharedPref=PreferenceManager.getDefaultSharedPreferences(this);
	  try
	  {
		  curValue=Double.parseDouble((sharedPref.getString("barchangedvalue",Double.toString(curValue))));
	  }
	  catch (Exception e)
	  {
		  e.printStackTrace();
	  }
	  System.out.println("curValue :"+curValue);
	  tmp.remove(curIndex);
	  tmp.add(curIndex,X,curValue);
	  mDataset.removeSeries(0);
	  mDataset.addSeries(0,tmp);
	  
	  if (yMax<curValue)
		  yMax=curValue;
	  
	  setScreenScale();
	  PreferenceManager.getDefaultSharedPreferences(this).edit().clear().commit();
  }
}