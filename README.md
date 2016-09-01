# RecyclerViewCardGallery

## App capture
![RecyclerViewCardGallery.gif](http://7xqerh.com1.z0.glb.clouddn.com/RecyclerViewCardGallery.gif)

## Usage

调用`new PageScaleHelper().attachToRecyclerView(mRecyclerView);`扩展RecyclerView
```
final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
mRecyclerView.setLayoutManager(linearLayoutManager);
mRecyclerView.setAdapter(new CardAdapter());
// mRecyclerView绑定scale效果
new CardScaleHelper().attachToRecyclerView(mRecyclerView);
```

adapter在相应的位置调用
```
mCardAdapterHelper.onCreateViewHolder(parent, itemView);
mCardAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());
```

## Apk download
[app_debug.apk](./art/app_debug.apk)

## Reference
[LinearSnapHelper源码解析](http://huazhiyuan2008.github.io/2016/09/01/LinearSnapHelper源码解析)