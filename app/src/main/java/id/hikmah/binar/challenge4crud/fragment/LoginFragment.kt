package id.hikmah.binar.challenge4crud.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings.Secure.putInt
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.fragment.findNavController
import id.hikmah.binar.challenge4crud.MainActivity
import id.hikmah.binar.challenge4crud.R
import id.hikmah.binar.challenge4crud.database.NoteData
import id.hikmah.binar.challenge4crud.databinding.FragmentLoginBinding
import id.hikmah.binar.challenge4crud.helper.SharedPref
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private var mDb: NoteData? = null

    private lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDb = NoteData.getInstance(requireContext())
//        sharedPref = SharedPref.requireContext()

        moveToRegister()
        actionLogin()

    }
     private fun moveToRegister() {
         binding.btnToregist.setOnClickListener{
             findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
         }
     }

    private fun actionLogin() {
        binding.btnDaftar.setOnClickListener {
            val email = binding.editUsername.text.toString()
            val password = binding.editPassword.text.toString()
            if (validateLogin(email, password)) {
                    isLogin(email, password)
                }

        }

    }

    private fun validateLogin(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            binding.editUsername.error = "Masukkan username Anda"
            return false
        }

        if (password.isEmpty()) {
            binding.editPassword.error = "Masukkan password Anda"
            return false
        }
        return true
    }

    private fun isLogin(username: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = mDb?.userDao()?.checkLogin(username,password)
            if (result == null) {
                CoroutineScope(Dispatchers.Main).launch {
                    val sharedId = mDb?.userDao()?.getId(username)?.id!!
                    val editor = sharedPref.edit()
                    editor.apply {
                        putInt("KEY_ID", sharedId)
                        putString("KEY_USERNAME", username)
                        putBoolean("SHARED_LOGIN", true)
                    }
                    Toast.makeText(requireContext(), "berhasil login", Toast.LENGTH_SHORT).show()
//                    startActivity(Intent.(requireContext(), MainActivity::class.java))
//                    requireActivity().finish()
                }
            } else {
                if (result == null) {
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(requireContext(), "Username tidak terdaftar", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(requireContext(), "Password salah!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}