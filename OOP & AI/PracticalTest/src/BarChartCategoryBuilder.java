

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

public class BarChartCategoryBuilder extends Activity {
  private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
  private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
  private XYSeries mCurrentSeries;
  private XYSeriesRenderer mCurrentRenderer;
  private GraphicalView mChartView;
  private static final int BAR_CHART_CATEGORY_PREF_RESULT=2;
  double curValue;
  int curIndex;
  double xMax=-1;
  double yMax=-1;
  float correctionVal=0;
  ArrayList<String> data;
  ArrayList<String> category;
  
  int befA=0;
  int befR=0;
  int befG=0;
  int befB=0;//기존의 색과 차이를 두기 위함
  
  @Override
  protected void onSaveInstanceState(Bundle outState) 
  {
    super.onSaveInstanceState(outState);
    //다시 시작할 때를 대비해서 현재 데이터 저장.
    outState.putSerializable("dataset", mDataset);
    outState.putSerializable("renderer", mRenderer);
    outState.putSerializable("current_series", mCurrentSeries);
    outState.putSerializable("current_renderer", mCurrentRenderer);
  }
  @Override
  protected void onRestoreInstanceState(Bundle savedState) 
  {
    super.onRestoreInstanceState(savedState);
    //다시 시작했을 때, 저장했던 데이터 복구
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

    mRenderer.setPanEnabled(false, false);
    mRenderer.setApplyBackgroundColor(true);
    mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));
    
    DisplayMetrics metrics =getApplicationContext().getResources().getDisplayMetrics();
    correctionVal = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 13, metrics);	//화면 크기에 알맞게 폰트 생성을 위한 보정변수
    
    mRenderer.setAxisTitleTextSize(correctionVal);
    mRenderer.setChartTitleTextSize(correctionVal);
    mRenderer.setLabelsTextSize(correctionVal);
    mRenderer.setLegendTextSize(correctionVal);	//화면 크기에 따른 보정변수로 초기화
    mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
    mRenderer.setZoomButtonsVisible(true);
    mRenderer.setBarWidth(2*correctionVal);		//bar하나의 width 길이 결정
    mRenderer.setShowLabels(false);
    //만약, x가 string으로 카테고리형 데이터라면 category의 개수도 받고, 실제 데이터도 받아서 비교하는 과정이 필요하다.
 
    data=getIntent().getStringArrayListExtra(("data"));
    category=getIntent().getStringArrayListExtra("category");
    
    int size=category.size();
    int settingXval=5;
    int temp=0;
    for (int i=0;i<size;i++)
    {
    	System.out.println(data.get(i)+", "+category.get(i));
    	XYSeries series = new XYSeries(category.get(i));
        mDataset.addSeries(series);
        mCurrentSeries = series;
        
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        mRenderer.addSeriesRenderer(renderer);
        renderer.setColor(randomColor());//색상설정 -- scale은 랜덤한 같은 색상, category는 각각 다른 색상.
        renderer.setDisplayChartValues(true);
        renderer.setDisplayChartValuesDistance(10);
        renderer.setDisplayBoundingPoints(true);
        renderer.setChartValuesTextSize(correctionVal);
        mCurrentRenderer = renderer;
    	mCurrentSeries.add(settingXval,getNum(category.get(i)));
    	
    	if (temp<getNum(category.get(i)))
    		temp=getNum(category.get(i));
    	settingXval+=10;
    }
    System.out.println("initialize complete!");
    xMax=settingXval;
    yMax=temp;
    setScreenScale();
    /*
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
	});*/ //capture 부분
  }
  
  protected int getNum(String input)
  {
	  int size=data.size();
	  int count=0;
	  for (int i=0;i<size;i++)
	  {
		  if (data.get(i).equals(input))
		  {
			  count++;
		  }
	  }
	  return count;
  }
  
  public String getName(int index)
  {
	  return category.get(index); 
  }
  
  public int randomColor()
  {
	  Random dice=new Random();
	  int n_A=(dice.nextInt(64)+192+befA)%64+192;
	  int n_R=(dice.nextInt(64)+192+befR)%64+192;
	  int n_G=(dice.nextInt(64)+192+befG)%64+192;
	  int n_B=(dice.nextInt(64)+192+befB)%64+192;
	  
	  int output=Color.argb(n_A,n_R,n_G,n_B);
	  return output;
  }
  
  protected void setScreenScale()
  {
	  mRenderer.setXAxisMin(5-correctionVal/2);
	  mRenderer.setYAxisMin(0.0);
	  mRenderer.setXAxisMax(xMax+correctionVal/2);		//초기 화면의 x축 range를 0~데이터의 X의최댓값+보정값/2, y축도 마찬가지
	  mRenderer.setYAxisMax(yMax+correctionVal/2);
  }

  @Override
  protected void onResume() 
  {
    super.onResume();
    if (mChartView == null) 
    {
      LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
      mChartView = ChartFactory.getBarChartView(this, mDataset, mRenderer,BarChart.Type.DEFAULT);
      //BarChart생성
      mRenderer.setClickEnabled(true);
      mRenderer.setSelectableBuffer(10);
      mChartView.setOnClickListener(new View.OnClickListener() 
      {
        public void onClick(View v) 
        {
          SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
          if (seriesSelection != null) 
          {
            Toast.makeText(
                BarChartCategoryBuilder.this,
                "Category name: "+getName(seriesSelection.getSeriesIndex())
                    + ", Frequency="+ seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
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
		        curIndex=seriesSelection.getSeriesIndex();
				Intent myIntent=new Intent(BarChartCategoryBuilder.this,BarChartPreference.class);
				startActivityForResult(myIntent,BAR_CHART_CATEGORY_PREF_RESULT);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return false;
		}
	});*/ //longClicklistener 안씁니다.
      layout.addView(mChartView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    } 
    else 
    {
      mChartView.repaint();
    }
  }
  
  protected void onActivityResult(int requestCode,int resultCode,Intent data)
  {
	  super.onActivityResult(requestCode, resultCode,data);
	  if (requestCode==BAR_CHART_CATEGORY_PREF_RESULT)
	  {
		  changeSetting();
	  }
  }

  public void changeSetting()
  {
	  XYSeries tmp=mDataset.getSeriesAt(curIndex);		//눌러진 곳의 series삭제후 다시 집어넣자.
	  double X=tmp.getX(0);
	  XYSeries tmp1=new XYSeries("Categorical"+curIndex);
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
	  tmp1.add(X,curValue);
	  mDataset.removeSeries(curIndex);
	  mDataset.addSeries(curIndex,tmp1);
	  PreferenceManager.getDefaultSharedPreferences(this).edit().clear().commit();
  }
}