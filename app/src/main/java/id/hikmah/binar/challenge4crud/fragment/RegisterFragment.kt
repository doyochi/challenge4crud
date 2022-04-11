package id.hikmah.binar.challenge4crud.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import id.hikmah.binar.challenge4crud.R
import id.hikmah.binar.challenge4crud.database.NoteData
import id.hikmah.binar.challenge4crud.databinding.FragmentRegisterBinding
import id.hikmah.binar.challenge4crud.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private var mDb: NoteData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDb = NoteData.getInstance(requireContext())

        actionRegister()

    }

    private fun actionRegister() {
        binding.btnDaftar.setOnClickListener{
            val etUsername = binding.editUsername.text.toString()
            val etEmail = binding.editEmail.text.toString()
            val etPassworda = binding.editPassword1.text.toString()
            val etPasswordb = binding.editPassword2.text.toString()

            if (validateRegisterInput(etUsername, etEmail, etPassworda, etPasswordb)) {
                inputRegister(etUsername, etEmail, etPassworda)
            }
        }

    }

    private fun inputRegister(username: String, email: String, password: String) {
        val user = User(null, username, email, password)
        CoroutineScope(Dispatchers.IO).launch {
            val result = mDb?.userDao()?.insertUser(user)
            if (result != 0L) {
                CoroutineScope(Dispatchers.Main).launch {
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    Toast.makeText(requireContext(), "Registrasi Berhasil!", Toast.LENGTH_SHORT).show()
                }
            } else {
                val checkRegistered = mDb?.userDao()?.getUser(username)
                if (checkRegistered == null){
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(
                            requireContext(),
                            "Username telah terdaftar",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
        }
    }

    private fun validateRegisterInput(username: String, email: String, passworda: String, passwordb: String): Boolean {

        if (username.isEmpty()) {
            binding.editUsername.error = "Username harus diisi"
            return false
        }

        if (email.isEmpty()) {
            binding.editEmail.error = "Email harus diisi"
            return false
        }

        if (passworda.isEmpty()) {
            binding.editPassword1.error = "Password harus diisi"
            return false
        }

        if (passwordb.isEmpty()) {
            binding.editPassword2.error = "Silahkan konfirmasi password"
            return false
        }

        if (passworda != passwordb) {
            binding.editPassword1.error = "Password yang Anda masukkan tidak cocok"
            return false
        }
        return true
    }

}