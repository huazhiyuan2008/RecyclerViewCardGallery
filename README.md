# RecyclerViewCardGallery

RecyclerView实现Card Gallery效果，替代ViewPager方案。能够快速滑动并最终定位到居中位置

![RecyclerViewCardGallery.gif](https://github.com/huazhiyuan2008/RecyclerViewCardGallery/blob/master/art/RecyclerViewCardGallery.gif)

## Usage

调用`new PageScaleHelper().attachToRecyclerView(mRecyclerView);`扩展RecyclerView
```
final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
mRecyclerView.setLayoutManager(linearLayoutManager);
mRecyclerView.setAdapter(new CardAdapter());
// mRecyclerView绑定scale效果
new CardScaleHelper().attachToRecyclerView(mRecyclerView);
```

在adapter相应的位置调用
```
mCardAdapterHelper.onCreateViewHolder(parent, itemView);
mCardAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());
```

## Apk download
[app_debug.apk](https://github.com/huazhiyuan2008/RecyclerViewCardGallery/blob/master/art/app-debug.apk?raw=true)

## Reference
[LinearSnapHelper源码解析](http://huazhiyuan2008.github.io/2016/09/01/LinearSnapHelper源码解析)