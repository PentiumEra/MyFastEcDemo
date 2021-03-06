package haodong.com.latte.ec.main.sort.content;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import haodong.com.latte.ec.R;
import haodong.com.latte.ec.R2;
import haodong.com.latte_core.delegates.LatteDelegate;
import haodong.com.latte_core.net.RestClient;
import haodong.com.latte_core.net.callback.ISuccess;

/**
 * Created by linghaoDo on 2018/8/8
 */
public class ContentDelegate extends LatteDelegate {
    @BindView(R2.id.rv_list_content)
    RecyclerView mRecyclerView=null;
    private List<SectionBean>mData=null;
    // key
    private static final String ARG_CONTENT_ID = "CONTENT_ID";
    private int mContentId = -1;

    public static ContentDelegate newInstance(int contentId) {
        final Bundle args = new Bundle();
        args.putInt(ARG_CONTENT_ID, contentId);
        final ContentDelegate delegate = new ContentDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            mContentId = args.getInt(ARG_CONTENT_ID);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_list_content;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        /**
         * @param spanCount 左右能显示的几个数据
         * @param  形态
         */
        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        initData();


    }
    private void initData(){
        RestClient.builder()
                .url("data/sort_content_data_1.json?id="+mContentId)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        mData=new SectionDataConverter().convert(response);
                        final SectionAdapter sectionAdapter=
                                new SectionAdapter(R.layout.item_section_content,
                                        R.layout.item_section_header,mData);
                        mRecyclerView.setAdapter(sectionAdapter);
                    }
                })
                .build()
                .get();
    }

}
