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

7. CNN(Dog vs Cat) is a convolutional network to solve a image classification problem using data set Dog vs Cat.

猫狗分类的CNN，但是出现了过拟合，消除过拟合的方法有dropout，augment等。

8. RNN model to solve text classification.

简单的RNN分类问题，数据处理提到了Mask，与Mask Rcnn是否有联系？Keras中的Masking就是一种忽略padding的技术

9. RNN model to solbe text generation.

运用RNN（GRU）创建了一个莎士比亚作品的自动生成系统，将完整的莎士比亚作品作为训练集合输入，然后可以根据一个字符串开头自动生成莎士比亚风格的文字，但内容并不
一定可读。训练时以字符为单位，生成prediction时也是将字符单个输入模型，模型结合该字符与模型中rnn的隐藏context自动生成下一个字符。 

10.  Attention NN

带有attention的sequence to sequence model，完成了一个机器翻译任务。
