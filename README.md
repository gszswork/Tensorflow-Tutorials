# ipynb
1. MNIST2 is professional implementation of handwiritten number recognition implementation in tensorflow.

总的来说，这个实现更符合我面向对象的编程思路，设计了MyModel类，设计了train和test process，然后在五次迭代中进行学习。


2. keras primary image process is a primary implementation of imgae recognition. 

这次图像识别的网络设计仍然是MLP（多层感知器），并且将28 \* 28的输入数据faltten到784 \* 1的向量作为输入的。总体来说和MNIST2区别不大，同样的分类问题。


3. movie reviews is a binary classification model with embedding layer and uses validation set.

这个模型主要学习了针对不同长度的输入，如果将其转化为相同长度的输入并且通过MLP模型进行训练（通过pad_sequences），也使用了embedding层。


4. overfitting and underfitting contains 4 models with different scales to solve a same problem. We can mainly learn 'tensorboard'. 'regularition'.

这个模型采用不同深度MLP解决不同规模的分类问题，展示了过拟合等问题。主要可以熟悉学习率衰减，tensorboard的使用等。

5. word embedding use 'Embedding' to solve a binary classification problem (which is the same as 3. movie review).

这个模型的实现更注重embedding这门技术，比3更具体展示了如何运用embedding并且导出了embedding weights。

6. CNN(CIFAR-10) is a convolutional network to solve a image classification problem using data set CIFAR-10. 

从Keras角度来看，CNN的模型与MLP模型的区别主要体现在model.Sequential()函数中layers的设置，训练过程的时间明显变长。


