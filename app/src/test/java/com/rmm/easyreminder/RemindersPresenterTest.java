package com.rmm.easyreminder;

import android.content.Context;

import com.rmm.easyreminder.view.IReminders;
import com.rmm.easyreminder.view.RemindersActivity;
import com.rmm.easyreminder.view.RemindersPresenter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;

import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.Mock;
import org.mockito.Mockito;

@RunWith(MockitoJUnitRunner.class)
public class RemindersPresenterTest {

    @Mock
    private IReminders.IView mView;

    @Mock
    private IReminders.IModel mModel;

    @Mock
    private Context mContext;

    private RemindersPresenter mPresenter;

    @Before
    public void setUp()
    {
        mPresenter = new RemindersPresenter (mView, mContext);
    }

    @Test
    public void test ()
    {
//        Mockito.when (mView.sendFeedbackToast ("Test")).Mockito.thenReturn ();
        Mockito.verify(mView).sendFeedbackToast ("Test");
    }
}