# -*- coding: utf-8 -*-
"""MNIST2.ipynb

Automatically generated by Colaboratory.

Original file is located at
    https://colab.research.google.com/drive/1azyn8Q2eKSi7LHVBafyalWqllNqW2qCT
"""

import tensorflow as tf
print(tf.__version__)

from __future__ import absolute_import, division, print_function, unicode_literals
import tensorflow as tf

from tensorflow.keras.layers import Dense, Flatten, Conv2D
from tensorflow.keras import Model

mnist = tf.keras.datasets.mnist

(x_train, y_train), (x_test, y_test) = mnist.load_data()
x_train, x_test = x_train / 255.0, x_test / 255.0

x_train.shape

# add a channels dimension
x_train = x_train[..., tf.newaxis]
x_test  = x_test[...,tf.newaxis]

x_train.shape

# 使用tf.data来将数据集切分为batch以及混淆数据集
# from_tenfor_slices: create a dataset who's elements are the slices of given tenfor(s)
# shuffle 混淆数据 
# batch 切分为batch

train_ds = tf.data.Dataset.from_tensor_slices((x_train, y_train)).shuffle(10000).batch(32)
test_ds  = tf.data.Dataset.from_tensor_slices((x_test, y_test)).batch(32)

train_ds

# 使用Keras模型子类化(model subclassing) 构建模型
class MyModel (Model):
  def __init__(self):
    super(MyModel,self).__init__()
    self.conv1 = Conv2D(32, 3, activation = 'relu')
    self.flatten = Flatten()
    self.d1 = Dense(128, activation='relu')
    self.d2 = Dense(10, activation='softmax')

  def call(self, x):
    x = self.conv1(x)
    x = self.flatten(x)
    x = self.d1(x)
    return self.d2(x)
#第一次bug出现在这里没有加括号
# model = MyModel
model = MyModel()

# choose optimizer & loss_function
optimizer = tf.keras.optimizers.Adam()
loss_object = tf.keras.losses.SparseCategoricalCrossentropy()

# 选择衡量指标来度量模型的损失值loss 和 准确率 accuracy，这些指标在epoch上积累值，然后打印出整体结果
train_loss = tf.keras.metrics.Mean(name='train_loss')
train_accuracy = tf.keras.metrics.SparseCategoricalAccuracy(name='train_accuracy')

test_loss = tf.keras.metrics.Mean(name='test_loss')
test_accuracy = tf.keras.metrics.SparseCategoricalAccuracy(name='test_accuracy')

# 训练模型函数
@tf.function
def train_step(images,labels):
  with tf.GradientTape() as tape:
    predictions = model(images)
    loss = loss_object(labels, predictions)
# GradientTape has no attributes gradients (second bug)
  gradients = tape.gradient(loss, model.trainable_variables)
  optimizer.apply_gradients(zip(gradients, model.trainable_variables))
  train_loss(loss)
  train_accuracy(labels, predictions)

# 测试模型函数:
@tf.function
def test_step(images,labels):
  predictions = model(images)
  t_loss = loss_object(labels,predictions)

  test_loss(t_loss)
  test_accuracy(labels,predictions)

TOTALEPOCHS = 5

for epoch in range(TOTALEPOCHS):
  # 在下一个epoch开始时，重置评估指标
  train_loss.reset_states()
  train_accuracy.reset_states()
  test_accuracy.reset_states()
  test_loss.reset_states()

  for images,labels in train_ds:
    train_step(images,labels)
  
  for test_images, test_labels in test_ds:
    test_step(test_images, test_labels)

  template = 'Epoch {}, Loss: {}, Accuracy: {}, Test Loss: {}, Test_Accuracy: {}'
  print (template.format(epoch+1,
                         train_loss.result(),
                         train_accuracy.result(),
                         test_loss.result(),
                         test_accuracy.result()
                         )
        )