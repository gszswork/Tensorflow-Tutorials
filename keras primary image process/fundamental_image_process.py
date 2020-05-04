# -*- coding: utf-8 -*-
"""fundamental image process.ipynb

Automatically generated by Colaboratory.

Original file is located at
    https://colab.research.google.com/drive/17veHUIYOuorBWv_0NzLo_Qk887Tn9fCW
"""

import tensorflow as tf
from tensorflow import keras

import numpy as np
import matplotlib.pyplot as plt

"""matplot library 的 pyplot是python的类似matlab plot的工具，for example:
x

```
# 此内容为代码格式
import numpy as np
import matplotlib.pyplot as plt

x = np.arange(0, 5, 0.1)
# 0-5之间间隔为0.1
y = np.sin(x)
plt.plot(x,y)
```
"""

print (tf.__version__)

# import the Fashion MNIST dataset

fashion_mnist = keras.datasets.fashion_mnist
(train_images, train_labels),(test_images, test_labels) = fashion_mnist.load_data()

"""train_images.shape -> (60000,28,28) 

train_labels.shape -> (60000,)
each label is an integer between 0-9, which is familar to MNIST handwritting.
"""

# define the class names. The names are known from the dataset 

class_names = ['T-shirt', 'Trouser', 'Pullover', 'Dress', 'Coat',
               'Sandal',  'Shirt',   'Sneaker',  'Bag',   'Ankle boot']

# have a inspect of the data. (application of the plt)
plt.figure()
plt.imshow(train_images[0])
plt.colorbar
plt.grid(False) #grid 控制格子，一开始认为是grey。。。
plt.show()

# Scale the values in images to between 0-1 (To get rid of the disturbing influence of gradient exploding)
# To divide 255 is because the images are in bit-16 color, 2^16 = 256

train_images = train_images / 255.0
test_images = test_images / 255.0

plt.figure(figsize = (10,10))
for i in range(25):
  plt.subplot(5,5,i+1)  # subplot(nrows, ncols, index, **kwargs)
  plt.xticks([])
  plt.yticks([])
  plt.grid(False)
  plt.imshow(train_images[i], cmap=plt.cm.binary)
  plt.xlabel(class_names[train_labels[i]])
plt.show()

model = keras.Sequential([
  keras.layers.Flatten(input_shape=(28,28)),
  keras.layers.Dense(128, activation='relu'),
  keras.layers.Dense(10)
])

"""Explanation about Sequential:

Flatten 将input_shape 为 28*28 的数据扁平成（28*28，1）。
这个操作在我matlab实现的三层神经网络也如此实现。在全连接MLP中，没有卷积操作必须将数据改为vector。
Dense 就是fully connected layer
"""

model.compile(optimizer='adam',
              loss=tf.keras.losses.SparseCategoricalCrossentropy(from_logits=True),
              metrics=['accuracy'])

"""# Compiling the model:
Before the model is ready to be trained, it needs a few more settings.

1.   Loss function: 损失函数, 采用了交叉熵损失.
Using from_logits=True may be more numerically stable.是否进行逻辑回归。
2.   Optimizer: how the model is updated based on the data it sees and its loss function. In here we used Adam.
3.   Metrics: Used to monitor (supervise) the training and testing steps.
"""

# Train the model
# model.fit: Trains the model for a fixed number of epochs (iterations on a dataset).

model.fit(train_images, train_labels, epochs=10)

"""从上述训练Epoch的例子可以看出：
训练过程是在上一次Epoch的基础上继续的。每一个Epoch都对training set遍历一遍。
"""

test_loss, test_acc = model.evaluate(test_images, test_labels, verbose=2)

"""# So？训练结束了，如果利用已经训练好的模型进行新数据的预测？"""

probability_model = tf.keras.Sequential([model, tf.keras.layers.Softmax()])


# then if we get a new test set, we can define it as test_images. new test_images have no labels.

predictions = probability_model.predict(test_images)

predictions.shape

predictions[0]

x = np.arange (0,10,1)
y = predictions[0]
plt.plot(x,y)