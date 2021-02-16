import tensorflow.compat.v1 as tf
tf.disable_v2_behavior()
import numpy as np
import dlib
import matplotlib.pyplot as plt
import matplotlib.patches as patches
import numpy as np
import sys

def align_faces(img):
    dets = detector(img, 1)
    
    if len(dets) == 0:
        sys.exit()
    
    objs = dlib.full_object_detections()

    for detection in dets:
        s = sp(img, detection)
        objs.append(s)
        
    faces = dlib.get_face_chips(img, objs, size=256, padding=0.5)
    
    return faces


def preprocess(img):
    return img.astype(np.float32) / 127.5 - 1.


def postprocess(img):
    return ((img + 1.) * 127.5).astype(np.uint8)


if __name__ == '__main__':
    # Load Models

    detector = dlib.get_frontal_face_detector()
    sp = dlib.shape_predictor('models/shape_predictor_5_face_landmarks.dat')

    # Align Functionalize



    # Load BeautyGAN Pretrained

    sess = tf.Session()
    sess.run(tf.global_variables_initializer())

    saver = tf.train.import_meta_graph('models/model.meta')
    saver.restore(sess, tf.train.latest_checkpoint('models'))
    graph = tf.get_default_graph()

    X = graph.get_tensor_by_name('X:0') # source
    Y = graph.get_tensor_by_name('Y:0') # reference
    Xs = graph.get_tensor_by_name('generator/xs:0') # output

    # Preprocess and Postprocess Functions


    # Load Images

    # no makeup image(user image)
    no_makeup = dlib.load_rgb_image('media/images/ball2_encode.png')
    no_makeup_faces = align_faces(no_makeup)

    no_makeup_img = no_makeup_faces[0]

    X_img = preprocess(no_makeup_img)
    X_img = np.expand_dims(X_img, axis=0)

    # Load Images

    # custom image
    custom = dlib.load_rgb_image('media/images/ball1_encode.png')
    custom_faces = align_faces(custom)

    # custom image Run

    custom_img = custom_faces[0]

    Y_img = preprocess(custom_img)
    Y_img = np.expand_dims(Y_img, axis=0)

    output = sess.run(Xs, feed_dict={
        X: X_img,
        Y: Y_img
    })

    # Save Result Image
    plt.imsave('media/custom/custom.png', postprocess(output[0]))