package com.brucetoo.materilanewsapp.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brucetoo.materilanewsapp.R;

public class EmptyViewLayout {

	// ---------------------------
	// static variables
	// ---------------------------
	/**
	 * The empty state
	 */
	public final static int TYPE_EMPTY = 1;
	/**
	 * The loading state
	 */
	public final static int TYPE_LOADING = 2;
	/**
	 * The error state
	 */
	public final static int TYPE_ERROR = 3;
	/**
	 * The content view state
	 */
	public final static int TYPE_CONTENT_VIEW_STATE = 4;

	// ---------------------------
	// members variables
	// ---------------------------
	private Context mContext;
	private LinearLayout mBackgroundViews;
	private ViewGroup mLoadingView;
	private ViewGroup mEmptyView;
	private ViewGroup mErrorView;
	private View mContentView;
	private LayoutInflater mInflater;
	private boolean mViewsAdded;
	private View.OnClickListener mEmptyButtonClickListener;
	private View.OnClickListener mErrorButtonClickListener;

	// ---------------------------
	// default values
	// ---------------------------
	private int mEmptyType = TYPE_LOADING;
	private String mErrorMessage;
	private String mEmptyMessage;
	private String mErroButtonTitle = null;
	private String mEmptyButtonTitle = null;
	private boolean mShowEmptyButton = true;
	private boolean mShowErrorButton = true;
	private int mShortAnimationDuration;

	// ---------------------------
	// getters and setters
	// ---------------------------
	/**
	 * Gets the loading layout
	 * 
	 * @return the loading layout
	 */
	public ViewGroup getLoadingView() {
		return mLoadingView;
	}

	/**
	 * Gets the empty layout
	 * 
	 * @return the empty layout
	 */
	public ViewGroup getEmptyView() {
		return mEmptyView;
	}

	/**
	 * Gets the error layout
	 * 
	 * @return the error layout
	 */
	public ViewGroup getErrorView() {
		return mErrorView;
	}

	/**
	 * Gets the content view for which this library is being used
	 * 
	 * @return the content view
	 */
	public View getMainView() {
		return mContentView;
	}

	/**
	 * Gets the last set state of the content view
	 * 
	 * @return loading or empty or error
	 */
	public int getEmptyType() {
		return mEmptyType;
	}

	/**
	 * Sets the state of the empty view of the content view
	 * 
	 * @param emptyType
	 *            loading or empty or error
	 */
	public void setEmptyType(int emptyType) {
		this.mEmptyType = emptyType;
		changeEmptyType();
	}

	/**
	 * Gets the message which is shown when the content view could not be loaded
	 * due to some error
	 * 
	 * @return the error message
	 */
	public String getErrorMessage() {
		return mErrorMessage;
	}

	/**
	 * Sets the message to be shown when the content view could not be loaded
	 * due to some error
	 * 
	 * @param errorMessage
	 *            the error message
	 */
	public void setErrorMessage(String errorMessage) {
		this.mErrorMessage = errorMessage;
	}

	/**
	 * Sets the title for the error button
	 * 
	 * @param titleErrorButton
	 */
	public void setTitleErrorButton(String titleErrorButton) {
		this.mErroButtonTitle = titleErrorButton;
	}

	public String getTitleErrorButton() {
		return mErroButtonTitle;
	}

	/**
	 * Gets the message which will be shown when the content view is empty
	 * 
	 * @return the message which will be shown when the content view will be
	 *         empty
	 */
	public String getEmptyMessage() {
		return mEmptyMessage;
	}

	/**
	 * Sets the message to be shown when content view will be empty
	 * 
	 * @param emptyMessage
	 *            the message
	 */
	public void setEmptyMessage(String emptyMessage) {
		this.mEmptyMessage = emptyMessage;
	}

	/**
	 * Sets the title for the empty button
	 * 
	 * @param titleEmptyButton
	 */
	public void setTitleEmptyButton(String titleEmptyButton) {
		this.mEmptyButtonTitle = titleEmptyButton;
	}

	public String getTitleEmptyButton() {
		return mEmptyButtonTitle;
	}

	/**
	 * Gets the OnClickListener which perform when EmptyView was click
	 * 
	 * @return
	 */
	public View.OnClickListener getEmptyButtonClickListener() {
		return mEmptyButtonClickListener;
	}

	/**
	 * Sets the OnClickListener to EmptyView
	 * 
	 * @param emptyButtonClickListener
	 *            OnClickListener Object
	 */
	public void setEmptyButtonClickListener(View.OnClickListener emptyButtonClickListener) {
		this.mEmptyButtonClickListener = emptyButtonClickListener;
	}

	/**
	 * Gets the OnClickListener which perform when ErrorView was click
	 * 
	 * @return
	 */
	public View.OnClickListener getErrorButtonClickListener() {
		return mErrorButtonClickListener;
	}

	/**
	 * Sets the OnClickListener to ErrorView
	 * 
	 * @param errorButtonClickListener
	 *            OnClickListener Object
	 */
	public void setErrorButtonClickListener(View.OnClickListener errorButtonClickListener) {
		this.mErrorButtonClickListener = errorButtonClickListener;
	}

	/**
	 * Gets if a button is shown in the empty view
	 * 
	 * @return if a button is shown in the empty view
	 */
	public boolean isEmptyButtonShown() {
		return mShowEmptyButton;
	}

	/**
	 * Sets if a button will be shown in the empty view
	 * 
	 * @param showEmptyButton
	 *            will a button be shown in the empty view
	 */
	public void setShowEmptyButton(boolean showEmptyButton) {
		this.mShowEmptyButton = showEmptyButton;
	}

	/**
	 * Gets if a button is shown in the error view
	 * 
	 * @return if a button is shown in the error view
	 */
	public boolean isErrorButtonShown() {
		return mShowErrorButton;
	}

	/**
	 * Sets if a button will be shown in the error view
	 * 
	 * @param showEmptyButton
	 *            will a button be shown in the error view
	 */
	public void setShowErrorButton(boolean showErrorButton) {
		this.mShowErrorButton = showErrorButton;
	}

	// ---------------------------
	// private methods
	// ---------------------------

	private void changeEmptyType() {

		setDefaultValues();
		refreshMessages();
		refreshOnClickListeners();

		// insert views in the root view
		if (!mViewsAdded) {
			// init background views

			// TODO is better inflate the views
			// getLayoutInflater().inflate(layoutResID,ViewGroup);
			mBackgroundViews = new LinearLayout(mContext);
			LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
			mBackgroundViews.setBackgroundColor(Color.WHITE);
			mBackgroundViews.setGravity(Gravity.CENTER);
			mBackgroundViews.setLayoutParams(lp);
			mBackgroundViews.setOrientation(LinearLayout.VERTICAL);

			if (mEmptyView != null) {
				mBackgroundViews.addView(mEmptyView);
			}
			if (mLoadingView != null) {
				mBackgroundViews.addView(mLoadingView);
			}
			if (mErrorView != null) {
				mBackgroundViews.addView(mErrorView);
			}
			mViewsAdded = true;
			((ViewGroup) mContentView.getParent()).addView(mBackgroundViews);
		}

		// change empty type
		if (mContentView != null) {
			switch (mEmptyType) {
			case TYPE_EMPTY:
				mBackgroundViews.setVisibility(View.VISIBLE);
				if (mEmptyView != null) {
					mEmptyView.setVisibility(View.VISIBLE);
				}
				if (mErrorView != null) {
					mErrorView.setVisibility(View.GONE);
				}
				if (mLoadingView != null) {
					mLoadingView.setVisibility(View.GONE);
				}
				if (mContentView != null) {
					mContentView.setVisibility(View.GONE);
					mContentView.setEnabled(false);
				}
				break;
			case TYPE_ERROR:
				mBackgroundViews.setVisibility(View.VISIBLE);
				if (mEmptyView != null) {
					mEmptyView.setVisibility(View.GONE);
				}
				if (mErrorView != null) {
					mErrorView.setVisibility(View.VISIBLE);
				}
				if (mLoadingView != null) {
					mLoadingView.setVisibility(View.GONE);
				}
				if (mContentView != null) {
					mContentView.setVisibility(View.GONE);
					mContentView.setEnabled(false);
				}
				break;
			case TYPE_LOADING:
				mBackgroundViews.setVisibility(View.VISIBLE);
				if (mEmptyView != null) {
					mEmptyView.setVisibility(View.GONE);
				}
				if (mErrorView != null) {
					mErrorView.setVisibility(View.GONE);
				}
				if (mLoadingView != null) {
					mLoadingView.setVisibility(View.VISIBLE);
				}
				if (mContentView != null) {
					mContentView.setVisibility(View.GONE);
					mContentView.setEnabled(false);
				}
				break;
			case TYPE_CONTENT_VIEW_STATE:
				if (mEmptyView != null) {
					mEmptyView.setVisibility(View.GONE);
				}
				if (mErrorView != null) {
					mErrorView.setVisibility(View.GONE);
				}
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
					if (mContentView != null) {
						if (mLoadingView != null && mLoadingView.getVisibility() == View.VISIBLE) {
							crossfadeView();
						} else {
							mBackgroundViews.setVisibility(View.GONE);
							mContentView.setVisibility(View.VISIBLE);
						}
						mContentView.setEnabled(true);
					}
				} else {
					if (mLoadingView != null) {
						mLoadingView.setVisibility(View.GONE);
					}
					if (mContentView != null) {
						mBackgroundViews.setVisibility(View.GONE);
						mContentView.setVisibility(View.VISIBLE);
						mContentView.setEnabled(true);
					}
				}
				break;
			default:
				break;
			}
		}
	}

	private void refreshMessages() {
		if (mEmptyMessage != null) {
			((TextView) mEmptyView.findViewById(R.id.lab_message)).setText(mEmptyMessage);
		} else {
			((TextView) mEmptyView.findViewById(R.id.lab_message)).setText(mContext.getString(R.string.empty_msg));
		}

		if (mErrorMessage != null) {
			((TextView) mErrorView.findViewById(R.id.lab_message)).setText(mErrorMessage);
		} else {
			((TextView) mErrorView.findViewById(R.id.lab_message)).setText(mContext.getString(R.string.empty_msg_error));
		}

		if (mEmptyButtonTitle != null) {
			((Button) mEmptyView.findViewById(R.id.but_action)).setText(mEmptyButtonTitle);
		} else {
			((Button) mEmptyView.findViewById(R.id.but_action)).setText(mContext.getString(R.string.empty_msg_tryagain));
		}

		if (mErroButtonTitle != null) {
			((Button) mErrorView.findViewById(R.id.but_action)).setText(mErroButtonTitle);
		} else {
			((Button) mErrorView.findViewById(R.id.but_action)).setText(mContext.getString(R.string.empty_msg_tryagain));
		}
	}

	private void refreshOnClickListeners() {

		if (mShowEmptyButton && mEmptyButtonClickListener != null) {
			View emptyViewButton = mEmptyView.findViewById(R.id.but_action);
			if (emptyViewButton != null) {
				emptyViewButton.setOnClickListener(mEmptyButtonClickListener);
				emptyViewButton.setVisibility(View.VISIBLE);
			}
		}

		if (mShowErrorButton && mErrorButtonClickListener != null) {
			View errorViewButton = mErrorView.findViewById(R.id.but_action);
			if (errorViewButton != null) {
				errorViewButton.setOnClickListener(mErrorButtonClickListener);
				errorViewButton.setVisibility(View.VISIBLE);
			}
		}
	}

	private void setDefaultValues() {

		if (mEmptyView == null) {
			mEmptyView = (ViewGroup) mInflater.inflate(R.layout.layout_emptyview, null);
			if (mShowEmptyButton && mEmptyButtonClickListener != null) {
				View emptyViewButton = mEmptyView.findViewById(R.id.but_action);
				if (emptyViewButton != null) {
					emptyViewButton.setOnClickListener(mEmptyButtonClickListener);
					emptyViewButton.setVisibility(View.VISIBLE);
				}
			} else {
				View emptyViewButton = mEmptyView.findViewById(R.id.but_action);
				emptyViewButton.setVisibility(View.GONE);
			}
		}

		if (mLoadingView == null) {
			mLoadingView = (ViewGroup) mInflater.inflate(R.layout.layout_loadingview, null);
		}

		if (mErrorView == null) {
			mErrorView = (ViewGroup) mInflater.inflate(R.layout.layout_errorview, null);
			if (mShowErrorButton && mErrorButtonClickListener != null) {
				View errorViewButton = mErrorView.findViewById(R.id.but_action);
				if (errorViewButton != null) {
					errorViewButton.setOnClickListener(mErrorButtonClickListener);
					errorViewButton.setVisibility(View.VISIBLE);
				}
			} else {
				View errorViewButton = mErrorView.findViewById(R.id.but_action);
				errorViewButton.setVisibility(View.GONE);
			}
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	private void crossfadeView() {
		// Set the content view to 0% opacity but visible, so that it is visible
		// (but fully transparent) during the animation.
		mContentView.setAlpha(0f);
		mContentView.setVisibility(View.VISIBLE);

		// Animate the content view to 100% opacity, and clear any animation
		// listener set on the view.
		mContentView.animate().alpha(1f).setDuration(mShortAnimationDuration).setListener(null);

		// Animate the loading view to 0% opacity. After the animation ends,
		// set its visibility to GONE as an optimization step (it won't
		// participate in layout passes, etc.)
		mBackgroundViews.animate().alpha(0f).setDuration(mShortAnimationDuration);
		mLoadingView.animate().alpha(0f).setDuration(mShortAnimationDuration)
				.setListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						mLoadingView.setVisibility(View.GONE);
						mLoadingView.setAlpha(1f);
						mBackgroundViews.setVisibility(View.GONE);
						mBackgroundViews.setAlpha(1f);
					}
				});
	}

	// ---------------------------
	// public methods
	// ---------------------------

	/**
	 * Constructor
	 * 
	 * @param context
	 *            the context (preferred context is any activity)
	 * @param contentView
	 *            the contentView for which this library is being used, this
	 *            view can't be the root view of the hierarchy, It has to be a
	 *            view (Any LinearLayout, RelativeLayout, View, etc) inside the
	 *            root view
	 * 
	 *            El content view para la cual estas usando esta lib, este
	 *            content view no puede ser el view raiz de la jerarquia, tiene
	 *            que ser un view cualquiera como LinearLayout, RelativeLayout,
	 *            etc. que este dentro del view raiz.
	 */
	public EmptyViewLayout(Context context, View contentView) {
		mContext = context;
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mContentView = contentView;
		mShortAnimationDuration = mContext.getResources().getInteger(android.R.integer.config_shortAnimTime);
		mEmptyMessage = mContext.getString(R.string.empty_msg);
		mErrorMessage = mContext.getString(R.string.empty_msg_error);
	}

	/**
	 * Shows the empty layout if the content view is empty
	 */
	public void showEmpty() {
		this.mEmptyType = TYPE_EMPTY;
		changeEmptyType();
	}

	/**
	 * Shows loading layout when a long task is doing
	 */
	public void showLoading() {
		this.mEmptyType = TYPE_LOADING;
		changeEmptyType();
	}

	/**
	 * Shows error layout when is there an error
	 */
	public void showError() {
		this.mEmptyType = TYPE_ERROR;
		changeEmptyType();
	}

	/**
	 * Shows the content view and hides the others overlays
	 */
	public void showContentView() {
		this.mEmptyType = TYPE_CONTENT_VIEW_STATE;
		changeEmptyType();
	}
}
