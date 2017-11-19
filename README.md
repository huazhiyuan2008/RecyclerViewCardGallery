# RecyclerViewCardGallery

声明：代码fork自 
[https://github.com/huazhiyuan2008/RecyclerViewCardGallery/](https://github.com/huazhiyuan2008/RecyclerViewCardGallery/)
感谢原作者

RecyclerView实现循环banner，替代ViewPager方案。能够快速滑动并最终定位到居中位置

![RecyclerViewCardGallery.gif](https://github.com/zjw-swun/RecyclerViewCardGallery/blob/master/art/RecyclerViewCardGallery.gif)

录制效果有点渣，见谅~ 可下载[apk](https://github.com/zjw-swun/RecyclerViewCardGallery/blob/master/art/app-debug.apk?raw=true)自己玩

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
[app_debug.apk](https://github.com/zjw-swun/RecyclerViewCardGallery/blob/master/art/app-debug.apk?raw=true)

## GPU Render测试图
[RecyclerViewCardGallery.gif](https://github.com/zjw-swun/RecyclerViewCardGallery/blob/master/art/RecyclerViewCardGallery_GPU.gif)



## License

```
Copyright 2016 zjw-swun

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```