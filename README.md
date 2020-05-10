# ipynb
1. MNIST2 is professional implementation of handwiritten number recognition implementation in tensorflow.

总的来说，这个实现更符合我面向对象的编程思路，设计了MyModel类，设计了train和test process，然后在五次迭代中进行学习。



2. keras primary image process is a primary implementation of imgae recognition. 

这次图像识别的网络设计仍然是MLP（多层感知器），并且将28 \* 28的输入数据faltten到784 \* 1的向量作为输入的。总体来说和MNIST2区别不大，同样的分类问题。


3. movie reviews is a binary classification model with embedding layer and uses validation set.

这个模型主要学习了针对不同长度的输入，如果将其转化为相同长度的输入并且通过MLP模型进行训练（通过pad_sequences），也使用了embedding层。
